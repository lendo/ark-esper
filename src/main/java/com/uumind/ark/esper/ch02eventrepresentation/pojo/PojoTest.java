package com.uumind.ark.esper.ch02eventrepresentation.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class PojoTest {

	public static void main(String[] args) {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();  
		  
        EPAdministrator admin = epService.getEPAdministrator();  
  
        String product = Person.class.getName();
        // 不设置事件时间或长度窗口，将导致数据一直暂存于内存，内存不断上升
        String epl = "select name, age, address.note, phones('a'), children[0] from " + product + "";  
  
        EPStatement state = admin.createEPL(epl);  
        state.addListener(new PersonListener());  
  
        EPRuntime runtime = epService.getEPRuntime();  
        
        Person person1 = new Person();  
        person1.setName("person1");
        person1.setAge(20);
        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");
        person1.setChildren(list);
    	Map<String, Integer> phones = new HashMap<String, Integer>();
    	phones.put("a", 1);
    	phones.put("c", 4);
    	person1.setPhones(phones);
    	Address address = new Address();
    	address.setNote("test");
    	address.setNum("nuuuu");
    	person1.setAddress(address);
        runtime.sendEvent(person1);  
	}

}
