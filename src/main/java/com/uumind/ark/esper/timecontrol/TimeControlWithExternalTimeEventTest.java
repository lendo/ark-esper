package com.uumind.ark.esper.timecontrol;

import java.util.HashMap;
import java.util.Map;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.time.CurrentTimeEvent;

/**
 * 使用sendEvent(ExternalTimeEvent)的方式进行时间窗口的控制
 */
public class TimeControlWithExternalTimeEventTest {

	public static void main(String[] args) {
		Configuration config = new Configuration();
		config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
		EPAdministrator admin = epService.getEPAdministrator();

		Map<String, Object> apple = new HashMap<String, Object>();
		apple.put("id", int.class);
		apple.put("price", int.class);
		apple.put("eventMillis", long.class);

		admin.getConfiguration().addEventType("Apple", apple);
		
		String epl = "select irstream current_timestamp(), eventMillis, sum(price) from Apple.win:time(1 sec)";

		EPStatement state = admin.createEPL(epl);
		
		state.addListener(new UpdateListener(){
			public void update(EventBean[] newEvents, EventBean[] oldEvents) {
				if (newEvents != null) {
					System.out.println("NEW:" + newEvents[0].get("eventMillis") + " " + newEvents[0].get("sum(price)")  + " " + newEvents[0].get("current_timestamp()"));
				}
				
				if (oldEvents != null) {
					System.out.println("OLD:" + oldEvents[0].get("eventMillis") + " " + oldEvents[0].get("sum(price)"));
				}
			}
		});

		EPRuntime runtime = epService.getEPRuntime();
		
		runtime.sendEvent(new CurrentTimeEvent(1019));
		Map<String, Object> apple1 = new HashMap<String, Object>();
		apple1.put("id", 1);
		apple1.put("price", 1);
		apple1.put("eventMillis", 1019);
		runtime.sendEvent(apple1, "Apple");

		runtime.sendEvent(new CurrentTimeEvent(3356));
		Map<String, Object> apple2 = new HashMap<String, Object>();
		apple2.put("id", 2);
		apple2.put("price", 2);
		apple2.put("eventMillis", 3356);
		runtime.sendEvent(apple2, "Apple");

		runtime.sendEvent(new CurrentTimeEvent(4578));
		Map<String, Object> apple4 = new HashMap<String, Object>();
		apple4.put("id", 2);
		apple4.put("price", 2);
		apple4.put("eventMillis", 4578);
		runtime.sendEvent(apple4, "Apple");
		
		runtime.sendEvent(new CurrentTimeEvent(102));
		Map<String, Object> apple3 = new HashMap<String, Object>();
		apple3.put("id", 3);
		apple3.put("price", 5);
		apple3.put("eventMillis", 102);
		runtime.sendEvent(apple3, "Apple");
		
		runtime.sendEvent(new CurrentTimeEvent(6778));
		Map<String, Object> apple5 = new HashMap<String, Object>();
		apple5.put("id", 2);
		apple5.put("price", 2);
		apple5.put("eventMillis", 6778);
		runtime.sendEvent(apple5, "Apple");
	}

}
