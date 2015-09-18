package com.uumind.ark.esper.ch02eventrepresentation.pojo;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class PersonListener implements UpdateListener {

	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		if (newEvents != null) {
			System.out.println("name is " + newEvents[0].get("name"));
			System.out.println("age is " + newEvents[0].get("age"));
			System.out.println("address.note is " + newEvents[0].get("address.note"));
			System.out.println("phones('a') is " + newEvents[0].get("phones('a')"));
			System.out.println("children[0] is " + newEvents[0].get("children[0]"));
		}
	}

}
