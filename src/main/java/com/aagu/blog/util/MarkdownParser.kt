package com.aagu.blog.util

import com.vladsch.flexmark.ext.gitlab.GitLabExtension
import com.vladsch.flexmark.ext.tables.TablesExtension
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.parser.ParserEmulationProfile
import com.vladsch.flexmark.util.KeepType
import com.vladsch.flexmark.util.ast.Node
import com.vladsch.flexmark.util.options.DataHolder
import com.vladsch.flexmark.util.options.MutableDataSet


object MarkdownParser {
    val OPTIONS: DataHolder = MutableDataSet()
            .setFrom(ParserEmulationProfile.MARKDOWN)
            .set(Parser.REFERENCES_KEEP, KeepType.LAST)
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(HtmlRenderer.PERCENT_ENCODE_URLS, true)

            // for full GFM table compatibility add the following table extension options:
            .set(TablesExtension.COLUMN_SPANS, false)
            .set(TablesExtension.APPEND_MISSING_COLUMNS, true)
            .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
            .set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, true)
            .set(Parser.EXTENSIONS, listOf(TablesExtension.create(), GitLabExtension.create()))
            .toImmutable()

    val parser: Parser = Parser.builder(OPTIONS).build()
    val renderer: HtmlRenderer = HtmlRenderer.builder(OPTIONS).build()

    fun parser(markdown: String): String {
        val document: Node = parser.parse(markdown)
        return renderer.render(document)
    }
}