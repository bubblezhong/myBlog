import React from 'react';
export default class About extends React.Component {
  render() {
		const html = `
<div class="markdown-body"><h2>About the webmaster</h2><pre class="ql-syntax hljs json" spellcheck="false">{
    <span class="hljs-attr">"nickname"</span>: <span class="hljs-string">"DeAr、Me"</span>, 
    <span class="hljs-attr">"contact"</span>: {
        <span class="hljs-attr">"email"</span>: <span class="hljs-string">"1247930339@qq.com"</span>,
        <span class="hljs-attr">"QQ"</span>: <span class="hljs-string">"1247930339"</span>
    },
    <span class="hljs-attr">"location"</span>: <span class="hljs-string">"Fujian"</span>,
    <span class="hljs-attr">"skills"</span>: [<span class="hljs-string">"React"</span>, <span class="hljs-string">"Vue"</span>, <span class="hljs-string">"Nodejs"</span>, <span class="hljs-string">"Spring"</span>, <span class="hljs-string">"SpringMVC"</span>, <span class="hljs-string">"Mybatis"</span>, <span class="hljs-string">"Spring Boot"</span>, <span class="hljs-string">"Docker"</span>]
}
</pre><h2>About the website</h2><ul><li>技术栈：React, React Router, webpack, Mysql, Spring Boot</li><li>网站类别：响应式</li><li>对象存储：阿里云OSS</li><li>技术支持：阿里云ECS</li></ul><p><br></p></div>
		`
    return (
      <div dangerouslySetInnerHTML={{__html: html}}></div>
    );
  }
}