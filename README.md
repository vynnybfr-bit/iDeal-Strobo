# iDeal Strobo

Aplicativo Android experimental para controlar a luminária Bluetooth anunciada como `ISP-523D77`.

## Objetivo

Criar um padrão de 200 LEDs repetindo:
- 3 LEDs em branco quente, fixos;
- 1 LED em branco frio, piscando rapidamente.

## Estado atual

A primeira versão contém somente uma prévia visual do padrão e valida a compilação automática do APK pelo GitHub Actions.

**O controle Bluetooth ainda não está implementado.** O protocolo do controlador precisa ser identificado e validado antes de transmitir comandos. Não há chave AES, formato de pacote ou característica de escrita confirmados neste projeto.

## Gerar APK

A cada push na branch `main`, o GitHub Actions executa a compilação. O APK de teste fica nos artefatos da execução do workflow.
