import React, { Component } from "react";
import logo from "./logo.svg";
import "./App.css";
import Graph from "./components/Graph";
import Loading from "./components/Loading";
import axios from "axios";
import Navigation from "./components/Navigation";

class App extends Component {
  constructor() {
    super();
    this.state = {
      country: "",
    };
  }

  componentDidMount() {
    this.loadData();
  }

  fetchData = async (url) => {
    const res = await axios(url);
    return await res.data;
  };

  loadData = (url = "/api/data") => {
    this.fetchData(url).then((data) => {
      for (let i = 0; i < data.length; i++) {
        data[i].key = i + 1;
      }
      this.setState({ data });
    });
  };

  loadStateData = (country) => {
    if (typeof country != "string") {
      this.setState({ country: "" });
      this.loadData();
    } else {
      this.setState({ country: country });
      this.loadData("/api/data/" + country);
    }
  };

  render() {
    return (
      <React.Fragment>
        <Navigation
          data={this.state.data}
          loadStateData={this.loadStateData}
          country={this.state.country}
        ></Navigation>
      </React.Fragment>
    );
  }
}

export default App;
