/*
 * Copyright 2008-2010 Bay Area Software, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.bayareasoftware.chartengine.ds.util;

import org.w3c.dom.Node;

import java.util.HashMap;

public enum HtmlTag {
    TABLE, THEAD, TFOOT, TBODY, TH, TD, TR,     // Table tags
    TT, I, B, BIG, SMALL, EM, STRONG, DFN,
    CODE, SAMP, KBD, VAR, CITE, FONT, Q, S,
    STRIKE, PRE, U, UL, SUB, SUP, CENTER, BR,
    DEL, INS, P, HTML, BODY, UNKNOWN, A, SPAN,
    TITLE, H2, HEAD, ABBR;

    private static final HashMap<String, HtmlTag> tagTable =
        new HashMap<String, HtmlTag>();

    static {
        for (HtmlTag tag : HtmlTag.values()) {
            tagTable.put(tag.name().toLowerCase(), tag);
        }
    }

    public static HtmlTag getTag(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            String name = node.getNodeName();
            if (name != null) {
                HtmlTag tag = tagTable.get(name.toLowerCase());
                if (tag != null) return tag;
            }
        }
        return UNKNOWN;
    }
}
