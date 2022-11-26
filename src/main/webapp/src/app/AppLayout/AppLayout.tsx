/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
import * as React from 'react';
import { NavLink, useLocation, useHistory } from 'react-router-dom';
import {
  Nav,
  NavList,
  NavItem,
  NavExpandable,
  Page,
  PageHeader,
  PageSidebar,
  SkipToContent,
  KebabToggle,
  Avatar,
  Dropdown,
  DropdownGroup,
  DropdownToggle,
  DropdownItem,
  PageHeaderTools,
  PageHeaderToolsGroup,
  PageHeaderToolsItem,
  Button,
  ButtonVariant
} from '@patternfly/react-core';
import { generateRoutes, IAppRoute, IAppRouteGroup } from '@app/routes';
import logo from '@app/bgimages/tamer-header-logo.svg';
import imgAvatar from '@app/bgimages/imgAvatar.svg';
import CogIcon from '@patternfly/react-icons/dist/esm/icons/cog-icon';
import HelpIcon from '@patternfly/react-icons/dist/esm/icons/help-icon';
import Cookies from "js-cookie";
import {BellIcon} from "@patternfly/react-icons";

interface IAppLayout {
  children: React.ReactNode;
}

const AppLayout: React.FunctionComponent<IAppLayout> = ({ children }) => {
  const [isNavOpen, setIsNavOpen] = React.useState(true);
  const [isMobileView, setIsMobileView] = React.useState(true);
  const [isNavOpenMobile, setIsNavOpenMobile] = React.useState(false);
  const [isUser, setIsUser] = React.useState("anonymous");
  const [isKebabDropdownOpen, setKebabDropdownOpen] = React.useState(false);
  const [isDropdownOpen, setDropdownOpen] = React.useState(false);
  const [isLoged, setIsLoged] = React.useState(false);
  const history = useHistory();

  const parseJwt = (token) => {
    try {
      return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
      return null;
    }
  };

  React.useEffect(() => {
    let value = {};
    value = Cookies.getJSON('tamer-userinfo');
    if (value) {
      setIsLoged(true);
      // @ts-ignore
      setIsUser(value.username);
    }
  }, []);

  const onNavToggleMobile = () => {
    setIsNavOpenMobile(!isNavOpenMobile);
  };
  const onNavToggle = () => {
    setIsNavOpen(!isNavOpen);
  };
  const onPageResize = (props: { mobileView: boolean; windowSize: number }) => {
    setIsMobileView(props.mobileView);
  };

  const onKebabDropdownToggle = event => {
    setKebabDropdownOpen(!isKebabDropdownOpen)
  };

  const onKebabDropdownSelect = event => {
    setKebabDropdownOpen(!isKebabDropdownOpen);
  };

  const onDropdownSelect = event => {
    setDropdownOpen(!isDropdownOpen);
  };

  const onDropdownToggle = event => {
    setDropdownOpen(!isDropdownOpen);
  };

  function LogoImg() {
    const history = useHistory();
    function handleClick() {
      history.push('/');
    }
    return (
      <img src={logo} onClick={handleClick} alt="Tamer Logo" />
    );
  }

  function dropDownOptions(history) {
    const logout = () => {
      Cookies.remove('tamer-auth');
      // Reload page for activating login logic
      window.location.reload();
    };
    return { logout };
  }

  const { logout } = dropDownOptions(history);

  const kebabDropdownItems = [
    <DropdownItem key="key1">
      <CogIcon /> Settings
    </DropdownItem>,
    <DropdownItem key="key2">
      <HelpIcon /> Help
    </DropdownItem>
  ];
  const userDropdownItems = [
    <DropdownGroup key="group 2">
      <DropdownItem key="group 2 profile">My profile</DropdownItem>
      <DropdownItem key="group 2 user" component="button">
        User management
      </DropdownItem>
      <DropdownItem key="group 2 logout" onClick={logout}>Logout</DropdownItem>
    </DropdownGroup>
  ];

  const headerTools = (
    <PageHeaderTools>
      <PageHeaderToolsGroup
        visibility={{
          default: 'hidden',
          lg: 'visible'
        }} /** the settings and help icon buttons are only visible on desktop sizes and replaced by a kebab dropdown for other sizes */
      >
        <PageHeaderToolsItem>
          <Button aria-label="Nofication actions" variant={ButtonVariant.plain}>
            <BellIcon />
          </Button>
          <Button aria-label="Settings actions" variant={ButtonVariant.plain}>
            <CogIcon />
          </Button>
        </PageHeaderToolsItem>
        <PageHeaderToolsItem>
          <Button aria-label="Help actions" variant={ButtonVariant.plain}>
            <HelpIcon />
          </Button>
        </PageHeaderToolsItem>
      </PageHeaderToolsGroup>
      <PageHeaderToolsGroup>
        <PageHeaderToolsItem
          visibility={{
            lg: 'hidden'
          }} /** this kebab dropdown replaces the icon buttons and is hidden for desktop sizes */
        >
          <Dropdown
            isPlain
            position="right"
            onSelect={onKebabDropdownSelect}
            toggle={<KebabToggle onToggle={onKebabDropdownToggle} />}
            isOpen={isKebabDropdownOpen}
            dropdownItems={kebabDropdownItems}
          />
        </PageHeaderToolsItem>
        <PageHeaderToolsItem
          visibility={{ default: 'hidden', md: 'visible' }} /** this user dropdown is hidden on mobile sizes */
        >
          <Dropdown
            isPlain
            position="right"
            onSelect={onDropdownSelect}
            isOpen={isDropdownOpen}
            toggle={<DropdownToggle onToggle={onDropdownToggle}>{isUser}</DropdownToggle>}
            dropdownItems={userDropdownItems}
          />
        </PageHeaderToolsItem>
      </PageHeaderToolsGroup>
      <Avatar src={imgAvatar} alt="Avatar image" />
    </PageHeaderTools>
  );

  const Header = (
    <PageHeader
      logo={<LogoImg />}
      headerTools={headerTools}
      showNavToggle
      isNavOpen={isNavOpen}
      onNavToggle={isMobileView ? onNavToggleMobile : onNavToggle}
    />
  );

  const location = useLocation();

  const renderNavItem = (route: IAppRoute, index: number) => (
    <NavItem key={`${route.label}-${index}`} id={`${route.label}-${index}`}
             isActive={route.path === location.pathname}>
      <NavLink exact={route.exact} to={route.path}>
        {route.label}
      </NavLink>
    </NavItem>
  );

  const renderNavGroup = (group: IAppRouteGroup, groupIndex: number) => (
    <NavExpandable
      key={`${group.label}-${groupIndex}`}
      id={`${group.label}-${groupIndex}`}
      title={group.label}
      isActive={group.routes.some((route) => route.path === location.pathname)}
    >
      {group.routes.map((route, idx) => route.label && renderNavItem(route, idx))}
    </NavExpandable>
  );

  const Navigation = (
    <Nav id="nav-primary-simple" theme="dark">
      <NavList id="nav-list-simple">
        {generateRoutes().map(
          (route, idx) => route.label && (!route.routes ? renderNavItem(route, idx) : renderNavGroup(route, idx))
        )}
      </NavList>
    </Nav>
  );

  const Sidebar = (
    <PageSidebar
      theme="dark"
      nav={Navigation}
      isNavOpen={isMobileView ? isNavOpenMobile : isNavOpen} />
  );

  const pageId = 'primary-app-container';

  const PageSkipToContent = (
    <SkipToContent onClick={(event) => {
      event.preventDefault();
      const primaryContentContainer = document.getElementById(pageId);
      primaryContentContainer && primaryContentContainer.focus();
    }} href={`#${pageId}`}>
      Skip to Content
    </SkipToContent>
  );
  return (
    <Page
      mainContainerId={pageId}
      header={Header}
      sidebar={Sidebar}
      onPageResize={onPageResize}
      skipToContent={PageSkipToContent}>
      {children}
    </Page>
  );
};

export { AppLayout };
