package com.meajax.model.interfaces;

public interface Individual {
	
	/**
	 * 随机初始化个体
	 */
	public void generateIndividual();
	
	/**
	 * 获取个体的适应度
	 * @return
	 */
	public double getFitness();
	
	/**
	 * 个体序列化
	 * @return
	 */
	public String toString();
	
	
	/**
	 * 个体发生突变
	 */
	public void mutate();
	
	/**
	 * 交叉生成后代个体
	 * @param individual
	 * @return
	 */
	public Individual crossoverWith(Individual individual);
	
}
