package com.leozhang.portalssm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leozhang.portalssm.entity.Dept;
import com.leozhang.portalssm.entity.Menu;
import com.leozhang.portalssm.entity.User;
import com.leozhang.portalssm.entity.UserExample;
import com.leozhang.portalssm.mapper.DeptMapper;
import com.leozhang.portalssm.mapper.MenuMapper;
import com.leozhang.portalssm.mapper.UserMapper;
import com.leozhang.portalssm.service.UserService;
import com.leozhang.portalssm.util.ChangeChar;
import com.leozhang.portalssm.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;
    
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public String getUserList() {
        UserExample userEx = new UserExample();
        UserExample.Criteria criteria = userEx.createCriteria();
        criteria.andUsernameLike("admin");
        List<User> list = userMapper.selectByExample(userEx);
        return JSONObject.toJSONString(list);
    }

    @Override
    public String getMenuListForPage(int pno, int psize) {
        Page<Menu> p = PageHelper.startPage(pno, psize);
        List<Menu> list = menuMapper.selectByExample(null);
        PageInfo<Menu> page = new PageInfo<Menu>(list);
        JSONObject j = new JSONObject();
        System.out.println(page.getList());
        System.out.println(page.getPages());
        j.put("list",page.getList());
        j.put("page",page);
        return j.toJSONString();
    }

    @Override
    public List<Menu> testList(int pno, int psize, Model model) {
        Page<Menu> p = PageHelper.startPage(pno, psize);
        List<Menu> list = menuMapper.selectByExample(null);
        Page<Menu> page = (Page)list;
        System.out.println(p.getTotal());
        System.out.println(p.getResult());
        System.out.println(p.getPages());
        System.out.println(page.getResult());
        System.out.println(page.getPages());
        System.out.println(page.getTotal());
        System.out.println(page.getResult().get(0));
        model.addAttribute("list",list);
        model.addAttribute("page",page);
        model.addAttribute("pageSize",page.getPageSize());
        model.addAttribute("pages",page.getPages());
        model.addAttribute("total",page.getTotal());
        return page;
    }

    @Override
    public User findByUsername(String userName) {

        UserExample ex = new UserExample();
        UserExample.Criteria cri = ex.createCriteria();
        cri.andUsernameEqualTo(userName);
        List<User> list = userMapper.selectByExample(ex);
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public User login(String username, String password, HttpServletRequest request) {
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        User u = null;
        try {
            currentUser.login(token);

            u = (User)currentUser.getPrincipals().getPrimaryPrincipal();
            request.getSession().setAttribute("userInfo",u);

        }catch (UnknownAccountException e) {
            System.out.println("???????????????");
        } catch (IncorrectCredentialsException e) {
            System.out.println("???????????????");
        } catch (AuthenticationException e) {
            System.out.println("??????????????????");
        }

        return u;
    }

    @Override
    public Result getListForPage(int pno, int psize, String username, String sortField, String sortType) {
        Page<User> p = PageHelper.startPage(pno, psize);
        UserExample ue = new UserExample();
        UserExample.Criteria userCriteria = ue.createCriteria();
        if(username.trim().length()>0){
            userCriteria.andUsernameLike("%"+username+"%");
        }
        if(sortField.trim().length()>0){
            ue.setOrderByClause(ChangeChar.camelToUnderline(sortField,2) +" " +sortType);

        }

        userMapper.selectAllByExampleForPage(ue);



        return Result.end(200,p.getResult(),"????????????",p.getTotal());
    }

    @Override
    public void insertUser(User user) {
        user.setInsertTime(new Date());
        user.setFreeze(0);
        String oldPassword = user.getPassword();
        SimpleHash newPassword = new SimpleHash("MD5", oldPassword, "abc", 2);
        user.setPassword(newPassword.toString());
        userMapper.insert(user);
    }

    @Override
    public User findUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void editUser(User user) {
        String oldPassword = user.getPassword();
        SimpleHash newPassword = new SimpleHash("MD5", oldPassword, "abc", 2);
        user.setPassword(newPassword.toString());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public String changePassword(HttpSession session, User user, Model model) {
        String password = user.getPassword();
        String password1 = user.getPassword1();
        String password2 = user.getPassword2();
        if(password == null || password.trim().length()==0){
            model.addAttribute("error",true);
            model.addAttribute("msg","????????????????????????");
            return "system/password/password";
        }
        UserExample ue = new UserExample();
        UserExample.Criteria criteria = ue.createCriteria();
        SimpleHash passwordDecoded = new SimpleHash("MD5", password, "abc", 2);
        criteria.andUsernameEqualTo(user.getUsername())
                .andPasswordEqualTo(passwordDecoded.toString());
        List<User> list = userMapper.selectByExample(ue);
        model.addAttribute("formData",user);
        if(list.size() == 0){
            model.addAttribute("error",true);
            model.addAttribute("msg","??????????????????");
            return "system/password/password";
        }
        if(password.equals(password1)){
            model.addAttribute("error",true);
            model.addAttribute("msg","???????????????????????????");
            return "system/password/password";
        }
        if(!password1.equals(password2)){
            model.addAttribute("error",true);
            model.addAttribute("msg","??????????????????????????????");
            return "system/password/password";
        }
        SimpleHash password1Decoded = new SimpleHash("MD5", password1, "abc", 2);
        user.setPassword(password1Decoded.toString());
        userMapper.updateByPrimaryKeySelective(user);
//        session.invalidate();
        model.addAttribute("complete",true);
        return "system/password/password";
    }

    @Override
    public void bindDept(Long id, Long deptId) {
        User u = new User();
        u.setId(id);
        u.setDeptId(deptId);
        userMapper.updateByPrimaryKeySelective(u);

    }

    /**
     * ??????????????????????????????????????????id??????????????????????????????????????????Map
     * @param deptId ??????????????????id
     * @param map ???????????????????????????map?????????list??????????????????????????????
     * @param list ???????????????????????????
     */
    public void getSelectById(Long deptId, Map<String,List<Dept>> map,List<Dept> list){
        //?????????????????????id??????????????????
        List<Dept> list1 = list.stream().filter(dept -> {
            if (dept.getId() == deptId) {
                return true;
            }else{
                return false;
            }
        }).collect(Collectors.<Dept>toList());
        //???????????????list???????????????????????????
        if(list1.size()>0){
            //????????????????????????
            Dept dept = list1.get(0);
            //?????????????????????pid????????????????????????l
            List<Dept> l = list.stream().filter(d -> {
                return d.getPid() == dept.getPid();
            }).collect(Collectors.toList());
            //??????????????????id??????-1?????????????????????????????????????????????????????????
            if(dept.getPid()!= -1){
                //??????dept-base?????????id@????????????id??????key???????????????????????????
                map.put("dept-base"+dept.getPid()+"@"+dept.getId(),l);
                //??????????????????????????????????????????
                getSelectById(dept.getPid(),map,list);
            }else{
                //??????id???-1?????????????????????dept-base@??????id???????????????????????????????????????
                map.put("dept-base@"+dept.getId(),l);
            }
        }
    }

    @Override
    public String getCheckedDeptList(User user) {
        //??????????????????????????????
        List<Dept> deptList = deptMapper.selectByExample(null);
        //????????????????????????map?????????????????????LinkedHashMap??????key?????????????????????
        Map<String,List<Dept>> map = new LinkedHashMap<String,List<Dept>>();
        //???????????????????????????????????????map????????????????????????key?????????key????????????select????????????????????????????????????
        getSelectById(user.getDeptId(),map,deptList);
        //??????entry??????
        Set<Map.Entry<String, List<Dept>>> set = map.entrySet();
        //???????????????????????????????????????
        String str = "";
        //???????????????map????????????????????????????????????select???option?????????
        for (Map.Entry<String, List<Dept>> entry:set) {
            //?????????map???key????????????select ???id
            String eleId = entry.getKey().split("@")[0];
            //?????????map???key?????????????????????????????????????????????id
            String selectedId = entry.getKey().split("@")[1];
            //??????select????????????
            String select = "<select id=\""+eleId+"\" class=\"layui-select\" lay-filter=\"dept-base\">" +
                    "<option value=\"\">?????????</option>";
            //??????option????????????????????????
            List<Dept> listDept = entry.getValue();
            //??????option??????
            for (Dept dept : listDept) {
                //????????????????????????option?????????
                String option = "<option value=\""+dept.getId()+"\" data-isLeaf=\""+dept.getIsLeaf()+"\" "
                        //???????????????????????????id?????????????????????id???????????????????????????????????????
                        +(Long.valueOf(selectedId)==dept.getId() ?"selected":"")
                        +">"
                        + dept.getName() +
                        "</option>";
                //?????????select????????????
                select+=option;
            }
            //select???????????????
            select += "</select>";
            //??????str??????????????????????????????????????????????????????????????????????????????????????????
            str =select+str;
        }
       // ??????????????????select?????????
        return str;
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectByExample(null);
    }
}
