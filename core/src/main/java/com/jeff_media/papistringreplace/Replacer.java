package com.jeff_media.papistringreplace;

import org.jetbrains.annotations.NotNull;

public interface Replacer {

    static String replace(@NotNull ReplaceArguments args) {
        return args.getText().replace(args.getSearch(), args.getReplace());
    }
}
