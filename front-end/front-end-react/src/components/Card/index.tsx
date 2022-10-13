import * as React from "react";
import Box from "@mui/material/Box";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import { Formulario } from "../Formulario";
import CardMedia from "@mui/material/CardMedia";
import { Collapse, Alert, IconButton } from "@mui/material";
import { useState } from "react";

export default function OutlinedCard() {
  const [sucesso, setSucesso] = useState(false);
  function handleAlterarStatusNotificacaoSucesso(status: boolean) {
    setSucesso(status);
  }

  const [falha, setFalha] = useState(false);
  function handleAlterarStatusNotificacaoFalha(status: boolean) {
    setFalha(status);
  }

  return (
    <Box sx={{ minWidth: 275 }}>
      <Card variant="outlined" style={{ borderRadius: "0.50rem" }}>
        <React.Fragment>
          <CardMedia
            component="img"
            height="200"
            image="https://pbs.twimg.com/profile_images/1120849099216052228/YWB0w5p4_400x400.jpg"
            alt="rato cego"
            style={{
              objectFit: "none",
              objectPosition: "center top",
            }}
          />
          <CardContent style={{ padding: "2rem" }}>
            <Typography
              gutterBottom
              variant="h5"
              component="div"
              style={{ padding: "1rem" }}
            >
              Validar Credenciais
            </Typography>
            <Formulario
              handleAlterarStatusNotificacaoSucesso={
                handleAlterarStatusNotificacaoSucesso
              }
              handleAlterarStatusNotificacaoFalha={
                handleAlterarStatusNotificacaoFalha
              }
            />
          </CardContent>
        </React.Fragment>
        <Collapse in={sucesso}>
          <Alert variant="outlined" severity="success">
            Ta validado meu nobre
          </Alert>
        </Collapse>
        <Collapse in={falha}>
          <Alert variant="outlined" severity="error">
            Ta errado isso ai
          </Alert>
        </Collapse>
      </Card>
    </Box>
  );
}
