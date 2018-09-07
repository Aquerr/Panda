package io.github.aquerr.pandobot.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface BotCommand
{
    long minRole();
    short argsCount();
}
