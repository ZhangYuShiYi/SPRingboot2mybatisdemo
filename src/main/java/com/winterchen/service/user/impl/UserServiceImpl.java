package com.winterchen.service.user.impl;

import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winterchen.dao.UserDao;
import com.winterchen.model.SysUser;
import com.winterchen.model.UserDomain;
import com.winterchen.mutildatabaseTransactionAop.MyDataSource;
import com.winterchen.service.user.UserService;
import com.winterchen.util.CacheConstant;
import com.winterchen.util.ExcelUtil;
import com.winterchen.util.SnowFlake;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.management.RuntimeErrorException;
import java.util.*;

/**
 * Created by zy on 2019/8/2.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;//这里会报错，但是并不会影响


    @Override
    public int addUser(UserDomain user) {

        return userDao.insert(user);
    }

    @Override
    public int addSysUser(SysUser user) {
        return userDao.addSysUser(user);
    }

    /*
    * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
    * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
    * pageNum 开始页数
    * pageSize 每页显示的数据条数
    * */
    @Override
    @Cacheable(value = CacheConstant.DOMAIN_INFO_CACHE)
    public PageInfo<UserDomain> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<UserDomain> userDomains = userDao.selectUsers();
        PageInfo result = new PageInfo(userDomains);
        return result;
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.DOMAIN_INFO_CACHE, key = "#userId")
    public UserDomain findUserById(Integer userId) {
        return userDao.findUserById(userId);
    }

    @Override
    public SysUser findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    @Override
    public Set<String> getUserRolesSet(String userName) {
        List<String> roles = userDao.getUserRolesSet(userName);
        return new HashSet<>(roles);
    }

    @Override
    public Set<String> getUserPermissionsSet(String userName) {
        List<String> permissions = userDao.getUserPermissionsSet(userName);
        return new HashSet<>(permissions);
    }

    @Override
    public LinkedList<String> getUserApiList(String userName) {
        return userDao.getUserApiList(userName);
    }

    @Override
    public List<SysUser> getUserList() {
        return userDao.getUserList();
    }

    @Override
    public int saveSysUser(SysUser sysUser) {
        return userDao.saveSysUser(sysUser);
    }

    @Override
    public String importExcel(MultipartFile file) {
        StringBuffer stringBuffer = new StringBuffer();
        ExcelImportResult<SysUser> result = ExcelUtil.importExcel(file, SysUser.class);
        if (result != null) {
            Sheet sheet = result.getWorkbook().getSheetAt(0);
            //行数
            int lineNum = sheet.getLastRowNum();
            if(0 == lineNum){
                stringBuffer.append("Excel内没有数据！");
            }
            //校验样品编号重复
            stringBuffer.append(checkData(sheet,lineNum));
            if (stringBuffer.length() != 0)
                return stringBuffer.toString();
            return "";
        }
        return "获取上传失败";
    }

    private String checkData(Sheet sheet,Integer lineNum){
        StringBuffer stringBuffer = new StringBuffer();
        SysUser model = null;
        //获得所有数据
        for(int i = 1; i <= lineNum; i++){
            model = new SysUser();
            int count = i + 1;// 记录实际行号
            Boolean flag = true;
            String userName = ExcelUtil.getRightTypeCell(sheet.getRow(i).getCell(0)).trim();		//userName
            String password = ExcelUtil.getRightTypeCell(sheet.getRow(i).getCell(1)).trim();		//password

            if(StringUtils.isBlank(userName)){
                stringBuffer.append("第"+ count +"行,用户名称不能为空</br>");
                flag = false;
            }

            if(StringUtils.isBlank(password)){
                stringBuffer.append("第"+ count +"行,用户密码不能为空</br>");
                flag = false;
            }
            /*if(StringUtils.isNotBlank(productCode)){
                ProductModel productModel =  productModelMapper.findByProductCode(productCode);
                if(productModel != null){
                    model.setProductCode(productCode);
                    model.setProductName(productModel.getName()!=null?productModel.getName():null);
                    model.setProductType(productModel.getDescription()!=null?productModel.getDescription():null);
                    model.setOrderNumber(getReplenishmentNo("".equals(productModel.getProductno())?"":productModel.getProductno()));
                }else{
                    stringBuffer.append("第"+ count +"行,商品编号"+productCode+"查询不到商品信息</br>");
                    flag = false;
                }
            }
            if(StringUtils.isBlank(num)){
                stringBuffer.append("第"+ count +"行,商品补货数量不能为空</br>");
                flag = false;
            }
            if(StringUtils.isNotBlank(num)){
                if(!BaseUtils.isInteger(num)){
                    stringBuffer.append("第"+ count +"行,商品补货数量不是数字</br>");
                    flag = false;
                }
            }

            if(StringUtils.isBlank(arrivalTime)){
                stringBuffer.append("第"+ count +"行,期望到货时间不能为空</br>");
                flag = false;
            }
            SimpleDateFormat sbf1 = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat sbf2 = new SimpleDateFormat("yyyy-MM-dd");
            if(StringUtils.isNotBlank(arrivalTime)){
                Date arrivalDate = null;
                try {
                    if(arrivalTime.indexOf("/") != -1){
                        arrivalDate = sbf1.parse(arrivalTime);
                    }else{
                        arrivalDate = sbf2.parse(arrivalTime);
                    }
                    model.setArrivalTime(arrivalDate);
                } catch (Exception e) {
                    stringBuffer.append("第"+ count +"行,期望到货时间格式有误</br>");
                    flag = false;
                }
            }

            if(StringUtils.isBlank(phone)){
                stringBuffer.append("第"+ count +"行,收货人电话号码不能为空</br>");
                flag = false;
            }
            if(StringUtils.isNotBlank(phone)){
                if(!BaseUtils.isMobile(phone)){
                    stringBuffer.append("第"+ count +"行,收货人电话号码格式有误</br>");
                    flag = false;
                }
            }

            if(StringUtils.isBlank(email)){
                stringBuffer.append("第"+ count +"行,业务邮箱不能为空</br>");
                flag = false;
            }
            if(StringUtils.isNotBlank(email)){
                if(!BaseUtils.isEmail(email)){
                    stringBuffer.append("第"+ count +"行,业务邮箱账号格式有误</br>");
                    flag = false;
                }
            }*/

            if(flag){
                model.setId(SnowFlake.getSnowFlake().nextId());
                model.setUserName(userName);
                model.setPassword(password);
                userDao.saveSysUser(model);
            }

        }
        if(stringBuffer.length() != 0){
            return stringBuffer.toString();
        }
        return "";
    }


    //mutildatabase
    @MyDataSource()
    @Override
    public List<SysUser> findAll1() {
        return userDao.getUserList();
    }

    @MyDataSource("datasource2")
    @Override
    public List<SysUser> findAll2() {
        return userDao.getUserList();
    }

    @MyDataSource()
    @SuppressWarnings("unused")
    @Override
    public Long add1(String userName,String password) {
        SysUser sysUser = new SysUser();
        Long id = SnowFlake.getSnowFlake().nextId();
        sysUser.setId(id);
        sysUser.setUserName(userName);
        sysUser.setPassword(password);
        int res = userDao.saveSysUser(sysUser);
        throw new RuntimeErrorException(new Error("error!!!!!"));
    }

    @MyDataSource("datasource2")
    @SuppressWarnings("unused")
    @Override
    public Long add2(String userName,String password) {
        SysUser sysUser = new SysUser();
        Long id = SnowFlake.getSnowFlake().nextId();
        sysUser.setId(id);
        sysUser.setUserName(userName);
        sysUser.setPassword(password);
        int res = userDao.saveSysUser(sysUser);
        throw new RuntimeErrorException(new Error("error!!!!!"));
    }




}
