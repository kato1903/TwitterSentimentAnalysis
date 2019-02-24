package Bayes2;

/**
 * A Java class that implements a simple text classifier, based on WEKA.
 * To be used with MyFilteredLearner.java.
 * WEKA is available at: http://www.cs.waikato.ac.nz/ml/weka/
 * Copyright (C) 2013 Jose Maria Gomez Hidalgo - http://www.esp.uem.es/jmgomez
 *
 * This program is free software: you can redistribute it and/or modify
 * it for any purpose.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
 
import weka.core.*;
import weka.classifiers.meta.FilteredClassifier;
import java.util.List;
import java.util.ArrayList;
import java.io.*;


 public class MyFilteredClassifier {
	
	public MyFilteredClassifier() {
		super();
		durum = new int[3];
	}

	String text;
	
	public void setText(String text) {
		this.text = text;
	}

	Instances instances;
	
	FilteredClassifier classifier;
	
	int durum[];
	
	public void load(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line;
			text = "";
			while ((line = reader.readLine()) != null) {
                text = text + " " + line;
            }
			System.out.println("===== Loaded text data: " + fileName + " =====");
			reader.close();
			System.out.println(text);
		}
		catch (IOException e) {
			System.out.println("Problem found when reading: " + fileName);
		}
	}
			
	public void loadModel(String fileName) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            Object tmp = in.readObject();
			classifier = (FilteredClassifier) tmp;
            in.close();
 			System.out.println("===== Loaded model: " + fileName + " =====");
       } 
		catch (Exception e) {
			// Given the cast, a ClassNotFoundException must be caught along with the IOException
			System.out.println("Problem found when reading: " + fileName);
		}
	}
	
	/**
	 * This method creates the instance to be classified, from the text that has been read.
	 */
	public void makeInstance() {
		// Create the attributes, class and text
//		FastVector fvNominalVal = new FastVector(2);
		ArrayList<String> list = new ArrayList<String>(2);
		
//		fvNominalVal.addElement("spam");
//		fvNominalVal.addElement("ham");
		list.add("positive");
		list.add("negative");
		
		
		
//		Attribute attribute1 = new Attribute("class", fvNominalVal);
//		Attribute attribute2 = new Attribute("text",(FastVector) null);
		
		Attribute attribute3 = new Attribute("class", list);
		Attribute attribute4 = new Attribute("text",(List<String>) null);
		
		
		// Create list of instances with one element
//		FastVector fvWekaAttributes = new FastVector(2);
		ArrayList<Attribute> listAtt = new ArrayList<Attribute>(2);
		
		
		
//		fvWekaAttributes.addElement(attribute1);
//		fvWekaAttributes.addElement(attribute2);
		listAtt.add(attribute3);
		listAtt.add(attribute4);
		
		
		
//		instances = new Instances("Test relation", fvWekaAttributes, 1);
		
		instances = new Instances("Test relation", listAtt, 1);
		
		// Set class index
		instances.setClassIndex(0);
		// Create and add the instance
		DenseInstance instance = new DenseInstance(2);
		instance.setValue(attribute4, text);
		// Another way to do it:
		// instance.setValue((Attribute)fvWekaAttributes.elementAt(1), text);
		instances.add(instance);
// 		System.out.println("===== Instance created with reference dataset =====");
//		System.out.println(instances);
	}
	
	/**
	 * This method performs the classification of the instance.
	 * Output is done at the command-line.
	 */
	public void classify() {
		try {
			double pred = classifier.classifyInstance(instances.instance(0));
//			System.out.println("===== Classified instance =====");
//			System.out.println("Class predicted: " + " with value " + pred + instances.classAttribute().value((int) pred));
			
			if(pred == 0.0)
				durum[0]++;
			if(pred == 1.0)
				durum[1]++;
			
			instances.instance(0).setClassValue(pred);
//			System.out.println("Bakalım ne yazacak "+pred+" "+instances.instance(0).stringValue(0));
			
		}
		catch (Exception e) {
			System.out.println("Problem found when worst the text");
		}		
	}
	

	public void ayir(String text)
	{
		this.setText(text);
		this.makeInstance();
		this.classify();
	}
	
	public void bilgi()
	{
		System.out.println(durum[0]+" "+durum[1]);
		int toplam = durum[0] + durum[1];
		double oran = (double) durum[0] / (durum[0] + durum[1]) ;
		oran = oran*100;
		
		System.out.println("Toplam "+toplam+" Tweet Arasindan yuzde "+oran+" kadari positif");
	}
	
	public static void main (String[] args) {
	
		MyFilteredClassifier classifier;
//		String a = "C:/Users/Toprak/eclipse-workspace/weka-example-master/data/smstest.txt";
		String b = "C:/Users/Toprak/eclipse-workspace/twitter-crawler/data/model3";
		
//			System.out.println("Usage: java MyClassifier <fileData> <fileModel>");
		
			classifier = new MyFilteredClassifier();
//			classifier.load(a);
			classifier.loadModel(b);
			classifier.ayir("could wait so bad");
			classifier.ayir("how are you");
			classifier.ayir("movie is good");
			classifier.ayir("waiting to see avengers");
			classifier.ayir("so dissappointing");
			classifier.bilgi();
		
	}
}	
