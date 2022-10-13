import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import axios from "axios";
import { type } from "os";
import React from "react";
import { useState } from "react";
import "./style.css";

type FormularioProps = {
  handleAlterarStatusNotificacaoSucesso: (status: boolean) => void;
  handleAlterarStatusNotificacaoFalha: (status: boolean) => void;
};

export function Formulario({
  handleAlterarStatusNotificacaoSucesso,
  handleAlterarStatusNotificacaoFalha,
}: FormularioProps) {
  const [login, setLogin] = useState<string>("");
  const [senha, setSenha] = useState<string>("");

  async function handleValidarCredencial() {
    const url = "http://localhost:5000/usuario/validar-credencial";
    const body = {
      cpf: Number(login),
      senha,
    };
    try {
      const response = await axios.post(url, body);
      if (response.status === 204) {
        handleAlterarStatusNotificacaoSucesso(true);
        handleAlterarStatusNotificacaoFalha(false);
      }
      console.log(response.status);
    } catch (error) {
      handleAlterarStatusNotificacaoFalha(true);
      handleAlterarStatusNotificacaoSucesso(false);
    }
  }

  return (
    <div>
      <TextField
        required
        id="outlined-required"
        label="login"
        name="login"
        onChange={(e) => setLogin(e.target.value)}
        value={login}
      />
      <TextField
        id="outlined-password-input"
        style={{ marginTop: "1rem" }}
        label="Senha"
        type="password"
        name="senha"
        onChange={(e) => setSenha(e.target.value)}
        value={senha}
        autoComplete="current-password"
      />

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
