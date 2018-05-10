package com.spring.jdbcTemplate;

import java.beans.PropertyVetoException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionTemplate;

import com.itcast.dao.impl.UserTransferDaoImpl;
import com.itcast.domain.UserTransfer;
import com.mchange.v2.c3p0.ComboPooledDataSource;
//配置了文件，所以需要启动spring容器测试
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestJdbcTemplate {
	@Autowired
	UserTransferDaoImpl userDaoImpl;
	//测试配置
	@Test
	public void testJdbcTemplate() throws PropertyVetoException{
		//获取连接池对象
		ComboPooledDataSource dataSource=new ComboPooledDataSource();
		dataSource.setDriverClass("com.mysql.jdbc.Driver");
		dataSource.setJdbcUrl("jdbc:mysql:///ssh_spring");
		dataSource.setUser("root");
		dataSource.setPassword("123456");
		//有连接池创建创建jdbcTemplate对象
		/*DataSourceTransactionManager tst=new DataSourceTransactionManager(dataSource);
		TransactionTemplate ts=new TransactionTemplate(tst);*/
		//创建模板对象
		JdbcTemplate jt=new JdbcTemplate();
		jt.setDataSource(dataSource);
		UserTransfer  ut=new UserTransfer();
		ut.setName("张三");
		ut.setMoney(100000.0);
		//通过摸版操作数据库
		String sql="insert into userTransfer values(id,?,?);";
		jt.update(sql,ut.getName(),ut.getMoney());
	}
	//添加
	@Test
	public void testAdd(){
		UserTransfer user=new UserTransfer();
		user.setName("李四");
		user.setMoney(10000.0);
		int flag = userDaoImpl.addUser(user);
		if(flag==1){
			System.out.println("添加成功！");
		}
	}
	//修改
	@Test
	public void testUpdate(){
		
		String name="zhangsan";
		userDaoImpl.updateUser(name);
		
	}
		
	
	//删除
	@Test
	public void testDelete(){
		int i = userDaoImpl.deleteUser(2);
		if(i==1){
			System.out.println("删除成功！");
		}
	}
	/**
	 * 通过id查询
	 */
	@Test
	public void testfindUserByid(){
		UserTransfer finderUserByid = userDaoImpl.finderUserByid(3);
		System.out.println(finderUserByid);
	}
	/**
	 *查询值类型
	 */
	@Test
	public void testFindCount(){
		int fintCount = userDaoImpl.fintCount();
		System.out.println(fintCount);
	}
	/**
	 * 查询所有用户
	 */
	@Test
	public void testFindAll(){
		List<UserTransfer> list = userDaoImpl.findAll();
		for(UserTransfer user:list){
			System.out.println(user);
		}
	}
}
