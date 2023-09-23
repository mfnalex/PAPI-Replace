package com.jeff_media.papi_replace_expansion;

import org.jetbrains.annotations.NotNull;

public class ReplaceArguments {
    private final @NotNull String search;
    private final @NotNull String replace;
    private final @NotNull String text;

    public ReplaceArguments(@NotNull String search, @NotNull String replace, @NotNull String text) {
        this.search = search;
        this.replace = replace;
        this.text = text;
    }

    public @NotNull String getSearch() {
        return this.search;
    }

    public @NotNull String getReplace() {
        return this.replace;
    }

    public @NotNull String getText() {
        return this.text;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ReplaceArguments)) {
            return false;
        }
        final ReplaceArguments other = (ReplaceArguments) o;
        if (!other.canEqual((Object) this)) {
            return false;
        }
        final Object this$search = this.getSearch();
        final Object other$search = other.getSearch();
        if (this$search == null ? other$search != null : !this$search.equals(other$search)) {
            return false;
        }
        final Object this$replace = this.getReplace();
        final Object other$replace = other.getReplace();
        if (this$replace == null ? other$replace != null : !this$replace.equals(other$replace)) {
            return false;
        }
        final Object this$text = this.getText();
        final Object other$text = other.getText();
        if (this$text == null ? other$text != null : !this$text.equals(other$text)) {
            return false;
        }
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ReplaceArguments;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $search = this.getSearch();
        result = result * PRIME + ($search == null ? 43 : $search.hashCode());
        final Object $replace = this.getReplace();
        result = result * PRIME + ($replace == null ? 43 : $replace.hashCode());
        final Object $text = this.getText();
        result = result * PRIME + ($text == null ? 43 : $text.hashCode());
        return result;
    }

    public String toString() {
        return "ReplaceArguments(search=" + this.getSearch() + ", replace=" + this.getReplace() + ", text=" +
                this.getText() + ")";
    }
}
