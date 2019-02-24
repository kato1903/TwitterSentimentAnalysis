package WekaApi;
import java.util.logging.Level;
import java.util.logging.Logger;

//import Bayes.ModelGenerator;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
public class Demo {
	
	public Instances loadDataset(String path) {
        Instances dataset = null;
        try {
            dataset = DataSource.read(path);
            if (dataset.classIndex() == -1) {
                dataset.setClassIndex(dataset.numAttributes() - 1);
            }
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dataset;
    }
	
	public Classifier buildClassifier(Instances traindataset) {
//        MultilayerPerceptron m = new MultilayerPerceptron();
        NaiveBayes m = new NaiveBayes();
        try {
            m.buildClassifier(traindataset);

        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }
	
	public String evaluateModel(Classifier model, Instances traindataset, Instances testdataset) {
        Evaluation eval = null;
        try {
            // Evaluate classifier with test dataset
            eval = new Evaluation(traindataset);
            eval.evaluateModel(model, testdataset);
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return eval.toSummaryString("", true);
    }
	
	public void saveModel(Classifier model, String modelpath) {

        try {
            SerializationHelper.write(modelpath, model);
        } catch (Exception ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	public static void main(String[] args) throws Exception {
		
		Demo d = new Demo();
		String Path = "‪C:/Users/Toprak/Desktop/asd";
		d.loadDataset(Path);
		
	}

}
