sudo su - jpos -c "cd ~/gateway && git pull && gradle :modules:testbed:installA>
sudo systemctl restart jpos.service 
sudo systemctl status jpos.service