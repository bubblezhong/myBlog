import React from 'react';
import FontAwesome from 'react-fontawesome';

import {NavLink} from 'react-router-dom'


require('../../styles/style.css');
require('../../styles/animate-bg.css');
require('../../styles/sidebar.css');
 	
export default class SideBar extends React.Component {
  constructor(props){
    super(props);
    this.state = {
        toggleMenu: false
    };
  }

	toggleMenuEvent = () => {
		const toggleMenu = !this.state.toggleMenu;
		this.setState({toggleMenu: toggleMenu});
	}
	// toggleMenuEvent () {
	// 	this.setState({toggleMenu: !this.state.toggleMenu});
	// }


  render() {
		const toggleMenu = !this.state.toggleMenu;

		const sidebarBoxStyle = {
			left: toggleMenu && document.body.clientWidth < 500 ? '-100%' : '0'
		}
		const menuIcon = !toggleMenu ? 'arrow-left' : 'navicon';
		const menuIconStyle = {
			color: !toggleMenu ? '#fff' : '#88909a',
			position: !toggleMenu ? 'fixed' : 'relative'
		}
		const wrapperStyle = {
			boxShadow: !toggleMenu && document.body.clientWidth < 500 ? 'rgba(0, 0, 0,0.26) 2px 0px 10px' : 'none'
		}
    return (
    	<div>
	    	<div className="header-fixed">
	    		<span style={menuIconStyle} className="menu-icon" onClick={ this.toggleMenuEvent }><FontAwesome name={menuIcon} /></span>
					<div className="logo">iShare</div>
	    	</div>
	      <div style={sidebarBoxStyle} className="sidebar-box" onClick={ this.toggleMenuEvent}>
					<div className="wrapper" style={wrapperStyle}>
						{/*<div className="toggle-off"><FontAwesome name='arrow-left' /></div>*/}
						<div className="sidebar-content">
							<div className="avartar">
								<img src="http://oes-bucket.oss-cn-shanghai.aliyuncs.com/OES/images/avartor/avartar.jpg" alt=""/>
							</div>
							<div className="slogan">
								<h3 className="iShare">「 iShare 」</h3>
								<h4>stay hungry, stay foolish</h4>
							</div>
							<div>
								<ul className="menu">
									<li className="menu-active">
										<NavLink to="/article"><FontAwesome name='home' /><FontAwesome name='hand-o-right' />&emsp;博客</NavLink>
									</li>
									<li>
										<NavLink to="/category"><FontAwesome name='th-large' /><FontAwesome name='hand-o-right' />&emsp;分类</NavLink>
									</li>
									<li>
										<NavLink to="/tag"><FontAwesome name='tags' /><FontAwesome name='hand-o-right' />&emsp;标签</NavLink>
									</li>
									<li>
										<NavLink to="/archive"><FontAwesome name='list-ul' /><FontAwesome name='hand-o-right' />&emsp;归档</NavLink>
									</li>
									<li>
										<NavLink to="/about"><FontAwesome name='user' /><FontAwesome name='hand-o-right' />&emsp;关于</NavLink>
									</li>
									{/*<li>
										<NavLink to="/newArticle"><FontAwesome name='edit' /><FontAwesome name='hand-o-right' />&emsp;发表</NavLink>
									</li>*/}
								</ul>
							</div>
						</div>
							
					  <ul className="bg-bubbles">
					    <li></li>
					    <li></li>
					    <li></li>
					    <li></li>
					    <li></li>
					    <li></li>
					    <li></li>
					    <li></li>
					    <li></li>
					  </ul>
					</div>
	      </div>
    	</div>

    );
  }
}