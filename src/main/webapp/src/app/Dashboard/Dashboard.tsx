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
  CardBody,
  CardFooter,
  CardTitle,
  DescriptionList,
  DescriptionListDescription,
  DescriptionListGroup,
  DescriptionListTerm,
  EmptyState,
  EmptyStateVariant,
  Flex,
  FlexItem,
  Grid,
  GridItem,
  PageSection, PageSectionVariants,
  Spinner, Text, TextContent, TextVariants,
  Title, Toolbar, ToolbarContent
} from '@patternfly/react-core';
import Cookies from 'js-cookie';
import {AppLogin} from "@app/AppLogin/AppLogin";
import {PlusCircleIcon} from "@patternfly/react-icons";
import { ChartDonutThreshold, ChartDonutUtilization } from '@patternfly/react-charts';
import "./main.scss";

const Dashboard: React.FunctionComponent = () => {
  const [isLoged, setIsLoged] = React.useState(false);
  const [isLoading, setIsLoading] = React.useState(false);

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
           <Text component={TextVariants.h1}>Cluster Dashboard</Text>
         </TextContent>
      </PageSection>
      <PageSection>
        <Grid hasGutter>
          <GridItem span={10}>
            <Card>
              <CardTitle className="bg_title">Cluster Health</CardTitle>
              <CardBody>
                <Flex className="example-border" justifyContent={{ default: 'justifyContentSpaceBetween' }} flex={{ default: 'flex_1' }}>
                  <FlexItem style={{width: "18%"}}>
                    <Card>
                      <CardTitle>Kubernetes API</CardTitle>
                      <CardBody className="panel-health-font">UP</CardBody>
                    </Card>
                  </FlexItem>
                  <FlexItem style={{width: "18%"}}>
                    <Card>
                      <CardTitle>Object Store</CardTitle>
                      <CardBody className="panel-health-font">UP</CardBody>
                    </Card>
                  </FlexItem>
                  <FlexItem style={{width: "18%"}}>
                    <Card>
                      <CardTitle>Backend</CardTitle>
                      <CardBody className="panel-health-font" style={{color: "red"}}>ERR</CardBody>
                    </Card>
                  </FlexItem>
                  <FlexItem style={{width: "18%"}}>
                    <Card>
                      <CardTitle>Masters</CardTitle>
                      <CardBody className="panel-health-font" style={{color: "orange"}}>2/3</CardBody>
                    </Card>
                  </FlexItem>
                    <FlexItem style={{width: "18%"}}>
                    <Card>
                      <CardTitle>Workers</CardTitle>
                      <CardBody className="panel-health-font">4/4</CardBody>
                    </Card>
                  </FlexItem>
                </Flex>
              </CardBody>
            </Card>
          </GridItem>


          <GridItem span={2} rowSpan={2}  >
          {/* <GridItem span={4} rowSpan={2} mdRowSpan={4} lgRowSpan={4}> */}
          <Flex direction={{ default: 'column' }} alignSelf={{ default: 'alignSelfFlexEnd' }} >
            <Card>
              <CardTitle className="bg_title">Components Info</CardTitle>
              <CardBody>
                <DescriptionList>
                  <DescriptionListGroup>
                    <DescriptionListTerm>Kubernetes Version</DescriptionListTerm>
                    <DescriptionListDescription>1.25.4</DescriptionListDescription>
                  </DescriptionListGroup>
                  <DescriptionListGroup>
                    <DescriptionListTerm>Container Runtime CRI</DescriptionListTerm>
                    <DescriptionListDescription>CRI-O 1.23.4</DescriptionListDescription>
                  </DescriptionListGroup>
                  <DescriptionListGroup>
                    <DescriptionListTerm>Contanier Network CNI</DescriptionListTerm>
                    <DescriptionListDescription>Flannel 0.20.1</DescriptionListDescription>
                  </DescriptionListGroup>
                  <DescriptionListGroup>
                    <DescriptionListTerm>Operating System</DescriptionListTerm>
                    <DescriptionListDescription>Centos 8</DescriptionListDescription>
                  </DescriptionListGroup>
                  <DescriptionListGroup>
                    <DescriptionListTerm>Tamer Version</DescriptionListTerm>
                    <DescriptionListDescription>
                      <Button variant="link" isInline icon={<PlusCircleIcon />}>
                        Tamer 1.0.0-SNAPSHOT
                      </Button>
                    </DescriptionListDescription>
                  </DescriptionListGroup>
                  <DescriptionListGroup>
                    <DescriptionListTerm>Quarkus Version</DescriptionListTerm>
                    <DescriptionListDescription>11111</DescriptionListDescription>
                  </DescriptionListGroup>
                  <DescriptionListGroup>
                    <DescriptionListTerm>PatternFly Version</DescriptionListTerm>
                    <DescriptionListDescription>2015.11.1</DescriptionListDescription>
                  </DescriptionListGroup>
                </DescriptionList>
                <DescriptionList className="lorem">Lorem ipsum dolor sit amet consectetur adipisicing elit. Est itaque eveniet illo rem id et, voluptatibus quas quisquam illum vitae, facere nobis perspiciatis architecto officiis. Officia fugit magnam nam mollitia.Lorem ipsum, dolor sit amet consectetur adipisicing elit. Quasi, quidem. Dolores, repudiandae. Quod reiciendis voluptate ab repellendus quae reprehenderit ipsum consequuntur error qui, impedit placeat et deleniti explicabo maiores quia?Lorem ipsum dolor sit amet consectetur adipisicing elit.</DescriptionList>
              </CardBody>
            </Card>
            </Flex>
          </GridItem>



          <GridItem span={10}>
            <Card>
              <CardTitle className="bg_title">Cluster Utilization</CardTitle>
              <CardBody>
                <Flex className="example-border" justifyContent={{ default: 'justifyContentSpaceBetween' }}>
                  <FlexItem>
                    <Card id="utilization-card-4-card" component="div">
                      <CardTitle>
                        <Title headingLevel="h4" size="lg">
                          CPU Usage
                        </Title>
                      </CardTitle>
                      <CardBody>
                        <ChartDonutThreshold
                          ariaDesc="Mock storage capacity"
                          ariaTitle="Mock donut utilization chart"
                          constrainToVisibleArea={true}
                          data={[
                            { x: 'Warning at 60%', y: 60 },
                            { x: 'Danger at 90%', y: 90 }
                          ]}
                          height={200}
                          labels={({ datum }) => (datum.x ? datum.x : null)}
                          padding={{
                            bottom: 0,
                            left: 10,
                            right: 150,
                            top: 0
                          }}
                          width={350}
                        >
                          <ChartDonutUtilization
                            animate={{
                              onExit: {
                                duration: 800,
                                before: () => ({
                                  _y: 0
                                })
                              }
                            }}
                            data={{ x: 'Storage capacity', y: 80 }}
                            labels={({ datum }) => (datum.x ? `${datum.x}: ${datum.y}%` : null)}
                            legendData={[{ name: `Capacity: 80%` }, { name: 'Warning at 60%' }, { name: 'Danger at 90%' }]}
                            legendOrientation="vertical"
                            title="80%"
                            subTitle="of 100 GBps"
                            thresholds={[{ value: 60 }, { value: 90 }]}
                          />
                        </ChartDonutThreshold>{' '}
                      </CardBody>
                      <CardFooter>
                        <a href="#">See details</a>
                      </CardFooter>
                    </Card>
                  </FlexItem>
                  <FlexItem>
                    <Card id="utilization-card-4-card" component="div">
                      <CardTitle>
                        <Title headingLevel="h4" size="lg">
                          Memory Usage
                        </Title>
                      </CardTitle>
                      <CardBody>
                        <ChartDonutThreshold
                          ariaDesc="Mock storage capacity"
                          ariaTitle="Mock donut utilization chart"
                          constrainToVisibleArea={true}
                          data={[
                            { x: 'Warning at 60%', y: 60 },
                            { x: 'Danger at 90%', y: 90 }
                          ]}
                          height={200}
                          labels={({ datum }) => (datum.x ? datum.x : null)}
                          padding={{
                            bottom: 0,
                            left: 10,
                            right: 150,
                            top: 0
                          }}
                          width={350}
                        >
                          <ChartDonutUtilization
                            animate={{
                              onExit: {
                                duration: 800,
                                before: () => ({
                                  _y: 0
                                })
                              }
                            }}
                            data={{ x: 'Storage capacity', y: 80 }}
                            labels={({ datum }) => (datum.x ? `${datum.x}: ${datum.y}%` : null)}
                            legendData={[{ name: `Capacity: 80%` }, { name: 'Warning at 60%' }, { name: 'Danger at 90%' }]}
                            legendOrientation="vertical"
                            title="80%"
                            subTitle="of 100 GBps"
                            thresholds={[{ value: 60 }, { value: 90 }]}
                          />
                        </ChartDonutThreshold>{' '}
                      </CardBody>
                      <CardFooter>
                        <a href="#">See details</a>
                      </CardFooter>
                    </Card>
                  </FlexItem>
                  <FlexItem>
                    <Card id="utilization-card-4-card" component="div">
                      <CardTitle>
                        <Title headingLevel="h4" size="lg">
                          Storage Usage
                        </Title>
                      </CardTitle>
                      <CardBody>
                        <ChartDonutThreshold
                          ariaDesc="Mock storage capacity"
                          ariaTitle="Mock donut utilization chart"
                          constrainToVisibleArea={true}
                          data={[
                            { x: 'Warning at 60%', y: 60 },
                            { x: 'Danger at 90%', y: 90 }
                          ]}
                          height={200}
                          labels={({ datum }) => (datum.x ? datum.x : null)}
                          padding={{
                            bottom: 0,
                            left: 10,
                            right: 150,
                            top: 0
                          }}
                          width={350}
                        >
                          <ChartDonutUtilization
                            animate={{
                              onExit: {
                                duration: 800,
                                before: () => ({
                                  _y: 0
                                })
                              }
                            }}
                            data={{ x: 'Storage capacity', y: 40 }}
                            labels={({ datum }) => (datum.x ? `${datum.x}: ${datum.y}%` : null)}
                            legendData={[{ name: `Capacity: 80%` }, { name: 'Warning at 60%' }, { name: 'Danger at 90%' }]}
                            legendOrientation="vertical"
                            title="40%"
                            subTitle="of 100 GBps"
                            thresholds={[{ value: 60 }, { value: 90 }]}
                          />
                        </ChartDonutThreshold>{' '}
                      </CardBody>
                      <CardFooter>
                        <a href="#">See details</a>
                      </CardFooter>
                    </Card>
                  </FlexItem>
                </Flex>
              </CardBody>
            </Card>
          </GridItem>

          

          


          <GridItem span={10} rowSpan={2}>
            <Card>
              <CardTitle className="bg_title">Tamer services</CardTitle>
              <CardBody>
                <Flex className="example-border" justifyContent={{ default: 'justifyContentSpaceBetween' }}>
                  <FlexItem style={{width: "18%"}}>
                    <Card>
                      <CardTitle>Kubernetes API</CardTitle>
                      <CardBody className="panel-health-font">UP</CardBody>
                    </Card>
                  </FlexItem>
                  <FlexItem style={{width: "18%"}}>
                    <Card>
                      <CardTitle>Object Store</CardTitle>
                      <CardBody className="panel-health-font">UP</CardBody>
                    </Card>
                  </FlexItem>
                  <FlexItem style={{width: "18%"}}>
                    <Card>
                      <CardTitle>Backend</CardTitle>
                      <CardBody className="panel-health-font" style={{color: "red"}}>ERR</CardBody>
                    </Card>
                  </FlexItem>
                  <FlexItem style={{width: "18%"}}>
                    <Card>
                      <CardTitle>Masters</CardTitle>
                      <CardBody className="panel-health-font" style={{color: "orange"}}>2/3</CardBody>
                    </Card>
                  </FlexItem>
                  <FlexItem style={{width: "18%"}}>
                    <Card>
                      <CardTitle>Workers</CardTitle>
                      <CardBody className="panel-health-font">4/4</CardBody>
                    </Card>
                  </FlexItem>
                </Flex>
                <DescriptionList className="lorem"><p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Est itaque eveniet illo rem id et, voluptatibus quas quisquam illum vitae, facere nobis perspiciatis architecto officiis. Officia fugit magnam nam mollitia.Lorem ipsum, dolor sit amet consectetur adipisicing elit. Quasi, quidem. Dolores, repudiandae. Quod reiciendis voluptate ab repellendus quae reprehenderit ipsum consequuntur error qui, impedit placeat et deleniti explicabo maiores quia?Lorem ipsum dolor sit amet consectetur adipisicing elit.<br></br>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquid, impedit quidem. Dolore et, nihil beatae, fugiat quae a mollitia illum reiciendis repellendus accusamus voluptas rem adipisci cumque nemo facilis! Vitae?</p></DescriptionList>
                {/* <Text className="lorem">que nostrum consequatur impedit hic?<br></br>lorem<br></br><br></br></Text> */}
              </CardBody>
            </Card>
            
          </GridItem>


          <GridItem span={2} rowSpan={2} >
            <Card>
              <CardTitle className="bg_title">Cluster Features</CardTitle>
              <CardBody>
                <DescriptionList id="margin">
                  <DescriptionListGroup>
                    <DescriptionListTerm>Kubernetes Version</DescriptionListTerm>
                    <DescriptionListDescription>1.25.4</DescriptionListDescription>
                  </DescriptionListGroup>
                  <DescriptionListGroup>
                    <DescriptionListTerm>Container Runtime CRI</DescriptionListTerm>
                    <DescriptionListDescription>CRI-O 1.23.4</DescriptionListDescription>
                  </DescriptionListGroup>
                  <DescriptionListGroup>
                    <DescriptionListTerm>Contanier Network CNI</DescriptionListTerm>
                    <DescriptionListDescription>Flannel 0.20.1</DescriptionListDescription>
                  </DescriptionListGroup>
                  <DescriptionListGroup>
                    <DescriptionListTerm>Operating System</DescriptionListTerm>
                    <DescriptionListDescription>Centos 8</DescriptionListDescription>
                  </DescriptionListGroup>
                </DescriptionList>
                {/* <Text className="lorem"></Text> */}
              </CardBody>
            </Card>
          </GridItem>
        </Grid>
      </PageSection>
    </>
  )
}

export { Dashboard };
