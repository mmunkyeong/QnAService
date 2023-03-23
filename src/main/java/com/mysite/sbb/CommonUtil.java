package com.mysite.sbb;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;


@Component //스프링부트에 의해 관리되는 빈(bean, 자바 객체)으로 등록
public class CommonUtil {
    public String markdown(String markdown){
        Parser parser= Parser.builder().build();
        Node document= parser.parse(markdown);
        HtmlRenderer renderer=HtmlRenderer.builder().build();
        return renderer.render(document);
    }
}
