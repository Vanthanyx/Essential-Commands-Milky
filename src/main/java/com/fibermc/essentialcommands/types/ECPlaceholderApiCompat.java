package com.fibermc.essentialcommands.types;

import eu.pb4.placeholders.api.ParserContext;
import eu.pb4.placeholders.api.node.TextNode;
import eu.pb4.placeholders.api.node.parent.ParentNode;
import eu.pb4.placeholders.impl.GeneralUtils;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

@SuppressWarnings("UnstableApiUsage")
public final class ECPlaceholderApiCompat {
    private ECPlaceholderApiCompat() {}

    private static Text getText(TextNode node, ParserContext context) {
        return node instanceof ParentNode
            ? ECPlaceholderApiCompat.toText((ParentNode) node, context)
            : node.toText(context, true);
    }

    public static Text toText(ParentNode node, ParserContext context) {
        var children = node.getChildren();
        if (children.length == 0) {
            return Text.empty();
        } else if (children.length == 1 && children[0] != null) {
            var out = getText(children[0], context);
            if (GeneralUtils.isEmpty(out)) {
                return out;
            }

            return out.copy().fillStyle(out.getStyle());
        } else {
            MutableText base = Text.empty();

            for (TextNode child : children) {
                if (child != null) {
                    var childText = getText(child, context);

                    if (!GeneralUtils.isEmpty(childText)) {
                        base.append(childText);
                    }
                }
            }

            return base;
        }
    }
}
