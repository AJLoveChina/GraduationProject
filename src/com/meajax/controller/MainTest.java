package com.meajax.controller;

import java.util.ArrayList;
import java.util.List;

import com.meajax.model.base2.*;

public class MainTest {
	
	public static void main(String[] args) {
		
		// 随机初始化几个资源点
		List<Point> resourcePoints = new ArrayList<Point>();
		
		for (int i = 0; i < 5; i++) {
			resourcePoints.add(new Point(Point.Type.RESOURCE));
		}
		
		// 随机初始化几个灾害点
		List<Point> damagePoints = new ArrayList<Point>();
		for (int i = 0; i < 10; i++) {
			damagePoints.add(new Point(Point.Type.DAMAGE));
		}
		
		
		SchemePopulation pop = new SchemePopulation();
		pop.setResources(resourcePoints);
		pop.setDamages(damagePoints);
		
		pop.init();
		
		Run run = new Run(pop);
		run.start(100);
		
		
	}
}