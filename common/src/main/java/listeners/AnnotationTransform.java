package listeners;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class AnnotationTransform implements IAnnotationTransformer {
    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
                          Constructor constructor, Method method) {
        // ✅ Always set Retry class
        if (method != null && method.getName().equals("runScenario")) {
            annotation.setRetryAnalyzer(Retry.class);
        }
    }
}