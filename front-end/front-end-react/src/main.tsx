import React from "react";
import ReactDOM from "react-dom/client";
import Card from "./components/Card";
import "./index.css";

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <React.StrictMode>
    <div className="centralizaCard">
      <Card />
    </div>
  </React.StrictMode>
);
