package com.meajax.model.interfaces;

public interface  Population{
	
	/**
	 * 初始化种群
	 */
	public void init();
	
	
	/**
	 * 保存个体
	 * @param index
	 * @param individual
	 */
	public void saveIndividual(Individual individual);
	
	
	/**
	 * 获取个体
	 * @param index
	 * @return
	 */
	public Individual getIndividual(int index);
	
	/**
	 * 获取最优个体
	 * @return
	 */
	public Individual getFittest();
	
	/**
	 * 获取种群个体数量
	 * @return
	 */
	public int populationSize();
	
	
	/**
	 * 种群进化
	 * @return
	 */
	public Population evolution();
}
