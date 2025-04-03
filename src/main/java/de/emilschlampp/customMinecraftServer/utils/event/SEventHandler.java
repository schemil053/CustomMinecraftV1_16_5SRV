package de.emilschlampp.customMinecraftServer.utils.event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target(value={METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SEventHandler {

}
