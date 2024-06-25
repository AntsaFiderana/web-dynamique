package outils;
import java.io.File;
import java.util.List;
import java.lang.Class;
import java.net.URL;
import java.util.Enumeration;
import java.io.*;
import java.util.ArrayList;
public class Outil{
	public static List<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);

        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }

        ArrayList<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

     static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files == null) {
            return classes;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                Class classe=Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
                classes.add(classe);
                //System.out.println(classe.getName());
            }
        }
        return classes;
    }
}
/* private void init(String controllerpackagename)
    {
        try {
            List<Class<?>> classes=Outil.getClasses(controllerpackagename);
            for (Class<?> class1 : classes) {
                if(class1.isAnnotationPresent(annotations.AnnotationController.class))
                {
                    //System.out.println(class1.getName());
                    mescontrolleurs.add(class1.getName());
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/