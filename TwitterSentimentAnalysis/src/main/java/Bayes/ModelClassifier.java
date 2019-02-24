package Bayes;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.SerializationHelper;
/*
public class ModelClassifier {

    private Attribute iyi;
    private Attribute kötü;

    private ArrayList<Attribute> attributes;
    private ArrayList<String> classVal;
    private Instances dataRaw;


    public ModelClassifier() {
        iyi = new Attribute("iyi");
        kötü = new Attribute("kötü");
        attributes = new ArrayList<Attribute>();
        classVal = new ArrayList<String>();
        classVal.add("olumlu");
        classVal.add("olumsuz");
        classVal.add("notr");

        attributes.add(iyi);
        attributes.add(kötü);

        attributes.add(new Attribute("class", classVal));
        dataRaw = new Instances("TestInstances", attributes, 0);
        dataRaw.setClassIndex(dataRaw.numAttributes() - 1);
    }

    
    public Instances createInstance(double iyi, double kötü, double result) {
        dataRaw.clear();
        double[] instanceValue1 = new double[]{iyi, kötü, 0};
        dataRaw.add(new DenseInstance(1.0, instanceValue1));
        return dataRaw;
    }


    public String classifiy(Instances insts, String path) {
        String result = "Not classified!!";
        Classifier cls = null;
        try {
            cls = (Classifier) SerializationHelper.read(path);
            result = classVal.get((int) cls.classifyInstance(insts.firstInstance()));
        } catch (Exception ex) {
            Logger.getLogger(ModelClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }


    public Instances getInstance() {
        return dataRaw;
    }
    

} */
