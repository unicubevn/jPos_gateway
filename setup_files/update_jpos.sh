sudo su - jpos -c "cd ~/gateway && git pull && gradle :modules:atom-gateway:installApp
sudo systemctl restart jpos.service 
sudo systemctl status jpos.service