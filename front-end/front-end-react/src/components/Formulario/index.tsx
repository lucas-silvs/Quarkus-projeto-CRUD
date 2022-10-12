import Button from "@mui/material/Button";
import axios from "axios";
import React from "react";
import { useState } from "react";
import "./style.css";

export function Formulario() {
  const [login, setLogin] = useState<string>("");
  const [senha, setSenha] = useState<string>("");

  async function handleValidarCredencial() {
    console.log(login);
    console.log(senha);

    const url = "http://localhost:5000/usuario/validar-credencial";
    const body = {
      cpf: Number(login),
      senha,
    };

    const response = await axios.post(url, body);

    console.log(response.status);
  }

  return (
    <div>
      <form className="forms">
        <label className="labels">login</label>
        <input
          type="text"
          name="login"
          className="input"
          onChange={(e) => setLogin(e.target.value)}
          value={login}
        />
        <label className="labels">senha</label>
        <input
          type="password"
          name="senha"
          className="input"
          onChange={(e) => setSenha(e.target.value)}
          value={senha}
        />
      </form>
      <Button
        style={{ marginTop: "1rem" }}
        type="submit"
        variant="contained"
        onClick={handleValidarCredencial}
      >
        mandar bala
      </Button>
    </div>
  );
}
