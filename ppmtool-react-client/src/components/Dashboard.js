import React, { Component } from "react";
import ProjectItem from "./Project/ProjectItem";

export default class Dashboard extends Component {
  render() {
    return (
      <div>
        <h1>Welcome to the Dashboard</h1>
        <ProjectItem />
      </div>
    );
  }
}
