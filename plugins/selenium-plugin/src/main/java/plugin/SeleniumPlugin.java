package plugin;


import authentication.tasks.PerformAuthentication;
import authentication.tasks.PerformNavigation;
import config.WebCleanUpClass;
import config.WebInitializeClass;
import factories.TaskFactoryHelper;
import intarfaces.platform.IPlatformProvider;
import intarfaces.plugins.IPlugin;
import enums.PlatformType;
import intarfaces.init.ICleanUp;
import intarfaces.init.IInitialize;
import intarfaces.tasks.ITask;
import intarfaces.tasks.ITaskFactory;
import intarfaces.validations.IValidation;
import intarfaces.validations.IValidationFactory;

import java.util.List;

public class SeleniumPlugin implements IPlugin, IPlatformProvider {

    @Override
    public List<ITaskFactory<? extends ITask<?>>> getTaskFactories() {
        return List.of(
                TaskFactoryHelper.of(PerformAuthentication.class),
                TaskFactoryHelper.of(PerformNavigation.class)
                // Add more tasks like:
                // TaskFactoryHelper.of(AnotherTask.class)
        );
    }

    @Override
    public List<IValidationFactory<? extends IValidation<?>>> getValidationFactories() {
        return null;/*List.of(
                ValidationFactoryHelper.of(LoginValidation.class)
                // Add more validations as needed
        );*/
    }


    public void execute() {
        System.out.println("Executing Selenium Plugin...");
    }

    @Override
    public PlatformType getPlatformType() {
        return PlatformType.WEB;
    }

    @Override
    public IInitialize getInitializer() {
        return new WebInitializeClass();
    }

    @Override
    public ICleanUp getCleaner() {
        return new WebCleanUpClass();
    }
}
