package il.co.moveyorg.movey.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

import il.co.moveyorg.movey.di.component.ConfigPersistentComponent;

/**
 * A scoping annotation to permit dependencies conform to the life of the
 * {@link ConfigPersistentComponent}
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigPersistent {
}
