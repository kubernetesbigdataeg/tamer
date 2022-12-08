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
import {
  Button,
  Card,
  CardActions,
  CardBody,
  CardHeader,
  CardTitle,
  Checkbox,
  Dropdown,
  DropdownItem, DropdownSeparator,
  EmptyState,
  EmptyStateVariant,
  Gallery,
  KebabToggle,
  PageSection,
  PageSectionVariants,
  Spinner,
  TextContent, TextVariants, Text,
  Toolbar,
  ToolbarContent,
  GalleryItem
} from '@patternfly/react-core';
import {AppLogin} from "@app/AppLogin/AppLogin";
import Cookies from 'js-cookie';
import {TrashIcon} from "@patternfly/react-icons";
import icons from "@storybook/components/dist/icon/icons";

const Catalog: React.FunctionComponent = () => {
  const [isLoged, setIsLoged] = React.useState(false);
  const [isLoading, setIsLoading] = React.useState(false);
  const [filters, setFilters] = React.useState([{ products: [] }]);
  const [res, setRes] = React.useState([]);
  const [selectedItems, setSelectedItems] = React.useState([]);
  const [areAllSelected, setAreAllSelected] = React.useState(false);
  const [itemsCheckedByDefault, setItemsCheckedByDefault] = React.useState(false);
  const [isUpperToolbarDropdownOpen, setIsUpperToolbarDropdownOpen] = React.useState(false);
  const [isUpperToolbarKebabDropdownOpen, setIsUpperToolbarKebabDropdownOpen] = React.useState(false);
  const [isLowerToolbarDropdownOpen, setIsLowerToolbarDropdownOpen] = React.useState(false);
  const [isLowerToolbarKebabDropdownOpen, setIsLowerToolbarKebabDropdownOpen] = React.useState(false);
  const [isCardKebabDropdownOpen, setIsCardKebabDropdownOpen] = React.useState(false);
  const [activeItem, setActiveItem] = React.useState(0);
  const [splitButtonDropdownIsOpen, setSplitButtonDropdownIsOpen] = React.useState(false);
  const [page, setPage] = React.useState(1);
  const [perPage, setPerPage] = React.useState(10);
  const [totalItemCount, setTotalItemCount] = React.useState(10);

  const onHandleLogin = (value) => {
    setIsLoged(value);
  }

  React.useEffect(() => {
    let value = {};
    value = Cookies.getJSON('tamer-auth');
    if (value) {
      setIsLoged(true);
    } else {
      setIsLoading(true);
      setIsLoged(false);
      location.reload();
    }
  }, []);

  const toolbarKebabDropdownItems = [
    <DropdownItem key="link">Link</DropdownItem>,
    <DropdownItem key="action" component="button">
      Action
    </DropdownItem>,
    <DropdownItem key="disabled link" isDisabled>
      Disabled Link
    </DropdownItem>,
    <DropdownItem key="disabled action" isDisabled component="button">
      Disabled Action
    </DropdownItem>,
    <DropdownSeparator key="separator" />,
    <DropdownItem key="separated link">Separated Link</DropdownItem>,
    <DropdownItem key="separated action" component="button">
      Separated Action
    </DropdownItem>
  ];

  return (
    isLoading?
      <EmptyState variant={EmptyStateVariant.full}>
        <Spinner/>
      </EmptyState>
   : ! isLoged?
        <AppLogin handleLogin={onHandleLogin}/>
   :
        <>
          <PageSection variant={PageSectionVariants.light}>
            <TextContent>
              <Text component={TextVariants.h1}>Catalog</Text>
              <Text component={TextVariants.p}>This is a list of technologies ready to install.</Text>
            </TextContent>
            <Toolbar id="data-toolbar-group-types">
              <ToolbarContent>JAVI</ToolbarContent>
            </Toolbar>
          </PageSection>

          <PageSection>
            <Gallery hasGutter>
              <GalleryItem>Gallery Item</GalleryItem>
              <GalleryItem>Gallery Item</GalleryItem>
              <GalleryItem>Gallery Item</GalleryItem>
              <GalleryItem>Gallery Item</GalleryItem>
              <GalleryItem>Gallery Item</GalleryItem>
              <GalleryItem>Gallery Item</GalleryItem>
            </Gallery>
          </PageSection>
       </>
    )
}

export { Catalog };
