package com.uumind.ark.esper.ch02eventrepresentation.map;

import java.util.HashMap;
import java.util.Map;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.uumind.ark.esper.ch01overview.AppleListener;

public class MapTest {

	public static void main(String[] args) {
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		EPAdministrator admin = epService.getEPAdministrator();

		// Apple定义
		Map<String, Object> apple = new HashMap<String, Object>();
		apple.put("id", int.class);
		apple.put("price", int.class);

		// 注册Apple到Esper
		admin.getConfiguration().addEventType("Apple", apple);

		String epl = "select avg(price) from Apple.win:length_batch(3)";

		EPStatement state = admin.createEPL(epl);
		state.addListener(new AppleListener());

		EPRuntime runtime = epService.getEPRuntime();

		Map<String, Object> apple1 = new HashMap<String, Object>();
		apple1.put("id", 1);
		apple1.put("price", 5);
		runtime.sendEvent(apple1, "Apple");

		Map<String, Object> apple2 = new HashMap<String, Object>();
		apple2.put("id", 2);
		apple2.put("price", 2);
		runtime.sendEvent(apple2, "Apple");

		Map<String, Object> apple3 = new HashMap<String, Object>();
		apple3.put("id", 3);
		apple3.put("price", 5);
		runtime.sendEvent(apple3, "Apple");
	}

}
