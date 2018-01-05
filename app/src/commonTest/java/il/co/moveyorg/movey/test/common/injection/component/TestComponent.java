package il.co.moveyorg.movey.test.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import il.co.moveyorg.movey.di.component.ApplicationComponent;
import il.co.moveyorg.movey.test.common.injection.module.ApplicationTestModule;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
