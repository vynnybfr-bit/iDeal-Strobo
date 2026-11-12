# Protocolo BLE pesquisado

O projeto de referência público `8none1/idealLED` documenta o protocolo iDeal LED para dispositivos anunciados como `ISP-...`.

## UUIDs

- Serviço: `0000fff0-0000-1000-8000-00805f9b34fb`
- Comandos AES: `d44bc439-abfd-45a2-b575-925416129600`
- Dados de pintura em lote: `d44bc439-abfd-45a2-b575-92541612960a`
- Notificações: `d44bc439-abfd-45a2-b575-925416129601`

## AES

A referência documenta AES-128 ECB com a chave extraída do aplicativo iDeal LED. A chave fica no código do projeto e não é uma credencial pessoal.

## Pintura individual (graffiti)

O pacote claro tem 16 bytes:
`0D 44 4F 4F 44 01 00 INDEX MODE SPEED R G B BRIGHTNESS 00 00`

- modo 2: fixo
- modo 1: fade
- modo 0: flash
- velocidade e brilho: 0–100

O pacote é cifrado em AES-128 ECB e escrito na característica de comandos.

## Pintura em lote (DOOT)

O protocolo também documenta pintura em lote:
- comando DOOT cifrado na característica de comandos;
- dados de pintura sem cifrar na característica de dados;
- até 14 instruções por pacote de dados;
- cada instrução contém início, quantidade de LEDs, RGB, modo e velocidade;
- finalização com DOOTCP e DTARTCY cifrados.

## Cuidado

O próprio autor da implementação de referência relata ter danificado uma fita ao enviar dados inválidos. O protocolo ainda precisa ser validado no controlador ISP-523D77 específico antes de considerar o aplicativo seguro para uso diário. Não envie comandos desconhecidos nem altere parâmetros arbitrariamente.

Referência: https://github.com/8none1/idealLED
Arquivo técnico: https://github.com/8none1/idealLED/blob/main/att_protocol.md
