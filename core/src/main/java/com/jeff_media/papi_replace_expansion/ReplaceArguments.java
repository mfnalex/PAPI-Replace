package com.jeff_media.papi_replace_expansion;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class ReplaceArguments {
    private final @NotNull String search;
    private final @NotNull String replace;
    private final @NotNull String text;
}
