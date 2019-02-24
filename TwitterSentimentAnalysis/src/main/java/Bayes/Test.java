package Bayes;

import weka.classifiers.Classifier;
//import Bayes.ModelClassifier;
//import Bayes.ModelGenerator;
//import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.bayes.*;
import weka.core.Debug;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

/**
 *
 * @author Taha Emara 
 * Website: http://www.emaraic.com 
 * Email : taha@emaraic.com
 * Created on: Jul 1, 2017
 * Github link: https://github.com/emara-geek/weka-example
 */
public class Test {

    public static final String DATASETPATH = "‪C:/Users/Toprak/Desktop/asd.arff";
//    public static final String MODElPATH = "C:/Users/Toprak/eclipse-workspace/weka-example-master/data/model.bin";

    public static void main(String[] args) throws Exception {
        
        ModelGenerator mg = new ModelGenerator();

        Instances dataset = mg.loadDataset(DATASETPATH);

        Filter filter = new Normalize();


        dataset.randomize(new Debug.Random(1));// if you comment this line the accuracy of the model will be droped from 96.6% to 80%
        
        filter.setInputFormat(dataset);
        Instances datasetnor = Filter.useFilter(dataset, filter);

        Instances traindataset = new Instances(datasetnor, 0, 5);
        Instances testdataset = new Instances(datasetnor, 5, 1);

        // build classifier with train dataset             
        Classifier ann = (Classifier) mg.buildClassifier(traindataset);

        // Evaluate classifier with test dataset
        String evalsummary = mg.evaluateModel(ann, traindataset, testdataset);
        System.out.println("Evaluation: " + evalsummary);

        //Save model 
//        mg.saveModel(ann, MODElPATH);

        //classifiy a single instance 
//        ModelClassifier cls = new ModelClassifier();
//        String classname =cls.classifiy(Filter.useFilter(cls.createInstance(1.6, 0.2, 0), filter), MODElPATH);
//        System.out.println("\n The class name for the instance with petallength = 1.6 and petalwidth =0.2 is  " +classname);

    }

}
