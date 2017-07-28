/*
 * Copyright (C) 2014 Bob Browning
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qiaoshouliang.templates;

import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.impl.ConstantNode;
import com.intellij.codeInsight.template.impl.MacroCallNode;
import com.intellij.codeInsight.template.macro.VariableOfTypeMacro;
import com.intellij.psi.PsiElement;
import com.qiaoshouliang.internal.AbstractRichStringBasedPostfixTemplate;
import com.qiaoshouliang.macro.ToStringIfNeedMacro;
import com.qiaoshouliang.utils.AndroidPostfixTemplatesUtils;
import org.jetbrains.annotations.NotNull;

import com.qiaoshouliang.utils.AndroidClassName;

import static com.qiaoshouliang.utils.AndroidClassName.CONTEXT;
import static com.qiaoshouliang.utils.AndroidClassName.TOAST;



/**
 * Postfix template for android Toast.
 *
 * @author takahirom
 */
public class ToastTemplate extends AbstractRichStringBasedPostfixTemplate {

    public ToastTemplate() {
        this("toast");
    }

    public ToastTemplate(@NotNull String alias) {
        super(alias, "Toast.makeText(context, expr, Toast.LENGTH_SHORT).show();", AndroidPostfixTemplatesUtils.IS_NON_NULL);
    }


    @Override
    public String getTemplateString(@NotNull PsiElement element) {
        return getStaticPrefix(TOAST, "makeText", element) + "($context$, $expr$, Toast.LENGTH_SHORT).show()$END$";
    }

    @Override
    protected void addExprVariable(@NotNull PsiElement expr, Template template) {
        final ToStringIfNeedMacro toStringIfNeedMacro = new ToStringIfNeedMacro();
        MacroCallNode macroCallNode = new MacroCallNode(toStringIfNeedMacro);
        macroCallNode.addParameter(new ConstantNode(expr.getText()));
        template.addVariable("expr", macroCallNode, false);
    }

    @Override
    protected void setVariables(@NotNull Template template, @NotNull PsiElement element) {
        MacroCallNode node = new MacroCallNode(new VariableOfTypeMacro());
        node.addParameter(new ConstantNode(CONTEXT.toString()));
        template.addVariable("context", node, new ConstantNode(""), false);

    }
}
