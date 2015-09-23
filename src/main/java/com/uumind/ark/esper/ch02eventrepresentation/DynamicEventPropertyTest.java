package com.uumind.ark.esper.ch02eventrepresentation;

import java.util.HashMap;
import java.util.Map;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

/**
 * 动态事件属性的含义：
 * 在处理某类事件时，事前并不确定事件是否一定有某属性，所以可以在EPL中用如下语法：
 * select attr? from EventType
 * 如果新到的EventType中包含attr属性，在UpdateListener中就可以通过attr?来获取
 * 如果新到的EventType中没有attr属性，在UpdateListener中通过attr?获取的值为null
 * 所有获取到的属性的类型都是Object
 */
public class DynamicEventPropertyTest {

	public static void main(String[] args) {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();

		// Apple定义
		Map<String, Object> apple = new HashMap<String, Object>();
		apple.put("id", int.class);
		apple.put("price", int.class);

		// 注册Apple到Esper
		admin.getConfiguration().addEventType("Apple", apple);

		String epl = "select price?, id?, abs? from Apple.win:time(3 sec)";

		EPStatement state = admin.createEPL(epl);
		state.addListener(new UpdateListener(){
			public void update(EventBean[] newEvents, EventBean[] oldEvents) {
				if (newEvents != null) {
					System.out.println("Apple's price is " + newEvents[0].get("price?"));
					System.out.println("Apple's id is " + newEvents[0].get("id?"));
					System.out.println("Apple's abs is " + newEvents[0].get("abs?"));
					System.out.println("=================================");
				}
			}
		});

		EPRuntime runtime = epService.getEPRuntime();

		Map<String, Object> apple1 = new HashMap<String, Object>();
		apple1.put("id", 1);
		apple1.put("price", 5);
		apple1.put("abs", 5);
		runtime.sendEvent(apple1, "Apple");

		Map<String, Object> apple2 = new HashMap<String, Object>();
		apple2.put("id", 2);
		apple2.put("price", 2);
		runtime.sendEvent(apple2, "Apple");

		Map<String, Object> apple3 = new HashMap<String, Object>();
		apple3.put("id", 3);
		apple3.put("price", 4);
		runtime.sendEvent(apple3, "Apple");
	}

}
