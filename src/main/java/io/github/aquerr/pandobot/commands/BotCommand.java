package io.github.aquerr.pandobot.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
public @interface BotCommand
{
    long minRole();
    short argsCount();
}
