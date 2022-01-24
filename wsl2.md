# WSL2

For future reference, this is the working setup to access Joystream node running on WSL2 inside Windows.
Given that 
1) Windows machine has a fixed LAN IP address of, let's say, 192.168.0.42, 
2) The Joystream node runs with the following additional flags:
`--rpc-cors=all --ws-external --unsafe-rpc-external` (May also need to remove `--validator` flag)
3) The WSL2 has an IP address of 172.28.201.43 (seen using `ifconfig -a`)

Then I need to forward the ports like this in Admin Powershell: `netsh interface portproxy add v4tov4 listenport=9944 listenaddress=0.0.0.0 connectport=9944 connectaddress=172.28.201.43`

The connection to 192.168.0.42:9944 should work. Note that Windows defender rules for port 9944 are NOT needed.
