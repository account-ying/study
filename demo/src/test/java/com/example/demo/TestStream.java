package com.example.demo;



import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @Author lyy
 * @Date 2020/6/18 0018 15:46
 */
public class TestStream {
	/**
	 * 使用流一共需要三步：
	 * 1. 准备一个数据源
	 * 2. 执行中间操作 中间操作可以有多个，它们可以串连起来形成流水线。
	 * 3. 执行终端操作 执行终端操作后本次流结束，你将获得一个执行结果。
	 */
	@Test
	public void test(){
		List<Person> list=new ArrayList<>();
		list.add(new Person("xiaobai","china"));
		list.add(new Person("red","china"));
		list.add(new Person("yellow","china"));
		list.add(new Person("green","china"));
		list.add(new Person("blue","china"));
		List<String> result = list.stream()
				.map(Person::getName)
				.collect(toList());
		for (String s : result) {
			System.out.println(s);
		}
	}

	@Test
	public void testString(){
		StringBuffer sbf = new StringBuffer();
		sbf.append("str");

		StringBuilder builder = new StringBuilder();
		builder.append("str");
	}

	@Test
	public void method(){

	}
}

class Person{
	private String  name;
	private String country;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Person(String name, String country) {
		this.name = name;
		this.country = country;
	}

	public Person() {
	}


}
