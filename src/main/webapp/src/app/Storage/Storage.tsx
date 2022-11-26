import * as React from 'react';
import {Grid, GridItem, PageSection, Title} from '@patternfly/react-core';

const Storage: React.FunctionComponent = () => (
  <PageSection>
    <Title headingLevel="h1" size="lg">Storage Status</Title>
    <Grid hasGutter>
      <GridItem span={8}>span = 8</GridItem>
      <GridItem span={4} rowSpan={2}>
        span = 4, rowSpan = 2
      </GridItem>
      <GridItem span={2} rowSpan={3}>
        span = 2, rowSpan = 3
      </GridItem>
      <GridItem span={2}>span = 2</GridItem>
      <GridItem span={4}>span = 4</GridItem>
      <GridItem span={2}>span = 2</GridItem>
      <GridItem span={2}>span = 2</GridItem>
      <GridItem span={2}>span = 2</GridItem>
      <GridItem span={4}>span = 4</GridItem>
      <GridItem span={2}>span = 2</GridItem>
      <GridItem span={4}>span = 4</GridItem>
      <GridItem span={4}>span = 4</GridItem>
    </Grid>
  </PageSection>
)

export { Storage };
