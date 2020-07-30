import React, { Component } from "react";
import PropTypes from "prop-types";

import { Menu, Layout, Button } from "antd";
import {
  ZoomOutOutlined,
  ArrowLeftOutlined,
  TableOutlined,
  OrderedListOutlined,
  UploadOutlined,
} from "@ant-design/icons";
import "antd/dist/antd.css";
import ListView from "./ListView";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  Redirect,
} from "react-router-dom";
import Graph from "./Graph";
import Loading from "./Loading";
import Head from "./Head";
import "react-icons/md";
import { MdLanguage } from "react-icons/md";
import Map from "./Map";
const { Header, Footer, Sider, Content } = Layout;

export default class Navigation extends Component {
  constructor() {
    super();
    this.state = {};
  }
  static propTypes = {
    prop: PropTypes,
  };

  getHighlightedMenu = () => {
    const url = window.location.href;

    if (url.endsWith("list")) {
      return "1";
    } else if (url.endsWith("graph")) {
      return "2";
    }
    return "0";
  };

  handleOnClick = () => {
    this.props.loadStateData("");
    this.setState({ redirect: true });
  };
  test = () => {};

  render() {
    return (
      <div>
        <Router>
          <Head
            loadStateData={this.props.loadStateData}
            country={this.props.country}
          />
          <Layout>
            <Sider trigger={null} collapsible collapsed={true}>
              <div className="logo" />
              <Menu
                theme="dark"
                mode="inline"
                defaultSelectedKeys={[this.getHighlightedMenu()]}
                style={{ marginTop: "-5px" }}
              >
                <Menu.Item key="1" icon={<OrderedListOutlined />}>
                  <Link to="/list"> List View</Link>
                </Menu.Item>
                <Menu.Item key="2" icon={<TableOutlined />}>
                  <Link to="/graph">Table View</Link>
                </Menu.Item>
                <Menu.Item key="3" icon={<ZoomOutOutlined />}>
                  <Link to="/map">Map View</Link>
                </Menu.Item>
                {this.props.country !== "" ? (
                  <Menu.Item key="-1" icon={<ArrowLeftOutlined />}>
                    <Link to="/" onClick={this.props.loadStateData}>
                      Go to World view
                    </Link>
                  </Menu.Item>
                ) : null}
              </Menu>
            </Sider>
            <Layout className="site-layout">
              <Switch>
                <Route exact path="/" onEnter={this.props.loadStateData}>
                  <ListView
                    data={this.props.data}
                    loadStateData={this.props.loadStateData}
                    country={this.props.country}
                  ></ListView>
                </Route>
                <Route path="/list">
                  <ListView
                    data={this.props.data}
                    loadStateData={this.props.loadStateData}
                    country={this.props.country}
                  ></ListView>
                </Route>
                <Route path="/graph">
                  {!this.props.data ? (
                    <Loading></Loading>
                  ) : (
                    <Graph
                      data={this.props.data}
                      loadStateData={this.props.loadStateData}
                      country={this.props.country}
                    ></Graph>
                  )}
                </Route>
                <Route path="/map">
                  {!this.props.data ? (
                    <Loading></Loading>
                  ) : (
                    <Content>
                      <Map
                        data={this.props.data}
                        loadStateData={this.props.loadStateData}
                        country={this.props.country}
                      ></Map>
                    </Content>
                  )}
                </Route>
              </Switch>
            </Layout>
          </Layout>
        </Router>
      </div>
    );
  }
}
