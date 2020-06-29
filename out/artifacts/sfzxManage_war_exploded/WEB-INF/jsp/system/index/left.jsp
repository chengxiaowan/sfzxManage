<nav class="navbar-default navbar-static-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <li class="nav-header">
                        <div class="dropdown profile-element">
                            <span><img src="/static/ui/img/profile_small.png" /></span>
                          <div class="text-muted text-center block" style="padding-top:10px"><span id="USERNAME"></span></div>
                        </div>
                        <a class="navbar-minimalize minimalize-styl-2" href="javascript:;"><i class="fa fa-bars"></i> </a>
                    </li>
                    <li>
                        <a class="J_menuItem" href="/workBench/myTaskList.do?myIndex=1">
                            <i class="fa fa-home"></i>
                            <span class="nav-label">主页</span>
                        </a>
                    </li>
                    <c:forEach items="${menuList}" var="menu1">
			<c:if test="${menu1.hasMenu && '1' == menu1.MENU_STATE}">
				<li class="" id="lm${menu1.MENU_ID }"><a class="dropdown-toggle"> <i class="${menu1.MENU_ICON == null ? 'menu-icon fa fa-leaf black' : menu1.MENU_ICON}"></i> <span
						class="nav-label"> ${menu1.MENU_NAME } </span> <c:if test="${'[]' != menu1.subMenu}">
							<span class="fa arrow"></span>
						</c:if>
				</a> <c:if test="${'[]' != menu1.subMenu}">
						<ul class="nav nav-second-level">
							<c:forEach items="${menu1.subMenu}" var="menu2">
								<c:if test="${menu2.hasMenu && '1' == menu2.MENU_STATE}">
									<li class="" id="z${menu2.MENU_ID }"><a class="J_menuItem" 
										<c:if test="${not empty menu2.MENU_URL && '#' != menu2.MENU_URL}"> href="${menu2.MENU_URL }"</c:if>
										<c:if test="${'[]' != menu2.subMenu}"> class="dropdown-toggle"</c:if>> ${menu2.MENU_NAME }  <c:if
												test="${'[]' != menu2.subMenu}">
												<span class="fa arrow"></span>
											</c:if>
									</a>  <c:if test="${'[]' != menu2.subMenu}">
											<ul class="nav  nav-third-level">
												<c:forEach items="${menu2.subMenu}" var="menu3">
													<c:if test="${menu3.hasMenu && '1' == menu3.MENU_STATE}">
														<li id="m${menu3.MENU_ID }"><a  class="J_menuItem"
															<c:if test="${not empty menu3.MENU_URL && '#' != menu3.MENU_URL}"> href="${menu3.MENU_URL }"</c:if>
															<c:if test="${'[]' != menu3.subMenu}"> class="dropdown-toggle"</c:if>> ${menu3.MENU_NAME }
																<c:if test="${'[]' != menu3.subMenu}">
																<span class="fa arrow"></span>
																</c:if>
														</a> <c:if test="${'[]' != menu3.subMenu}">
																<ul class="nav  nav-third-level">
																	<c:forEach items="${menu3.subMenu}" var="menu4">
																		<c:if test="${menu4.hasMenu && '1' == menu4.MENU_STATE}">
																			<li class="" id="n${menu4.MENU_ID }"><c:if test="${'[]' != menu4.subMenu}">
																					<a class="J_menuItem" href="menu/otherlistMenu.do?MENU_ID=${menu4.MENU_ID }">
																				</c:if> <c:if test="${'[]' == menu4.subMenu}">
																					<a class="J_menuItem" <c:if test="${not empty menu4.MENU_URL && '#' != menu4.MENU_URL}">target="mainFrame" href="${menu4.MENU_URL }"</c:if>>
																				</c:if> ${menu4.MENU_NAME } </a> </li>
																		</c:if>
																	</c:forEach>
																</ul>
															</c:if></li>
													</c:if>
												</c:forEach>
											</ul>
										</c:if></li>
								</c:if>
							</c:forEach>
						</ul>
					</c:if></li>
			</c:if>
		</c:forEach>
                </ul>
            </div>
        </nav>