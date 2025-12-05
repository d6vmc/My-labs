mkdir -p ~/lab0
cd ~/lab0
chmod -R 700 *
rm -rf *

# mkdir flygon5 flygon5/mankey flygon5/escavalier flygon5/gliscor flygon5/porygon

mkdir -p flygon5/mankey flygon5/escavalier flygon5/gliscor flygon5/porygon
touch flygon5/larvitar

mkdir -p hariyama0/nidorina hariyama0/whismur hariyama0/houndour hariyama0/pichu
touch hariyama0/silicoon hariyama0/spinarak

mkdir -p oshawott5/zorua oshawott5/elekid oshawott5/ursaring oshawott5/sunkern oshawott5/charmeleon
touch oshawott5/accelegor

touch purugly3 rattata4 venonat3


## 1

cat << EOF > flygon5/larvitar
Способности Bite Leer Sandstorm Screech Chip Away Rock
Slide Scary Face Thrash Dark Pulse Payback Crunch Earthquake Stone
Edge Hyper Beam
EOF

cat << EOF > hariyama0/silicoon
Тип диеты Nullivore
EOF

cat << EOF > hariyama0/spinarak
Тип покемона
BUG POISON
EOF

cat << EOF > oshawott5/accelegor
Тип диеты Herbivore
EOF

cat << EOF > purugly3
Ходы Covet Foul
Play Fury Cutter Hyper Voice Iron Tail Knock Off Last Resort Mud-Slap
Roll-out Shock Wave Sleep Talk Snatch Snore Sucker Punch Swift Super 
Fang Water Pulse
EOF

cat << EOF > rattata4
weight=7.7 height=12.0 atk=6
def=4
EOF

cat << EOF > venonat3
Развитые способности Run Away
EOF

## 2

chmod 550 flygon5
chmod 312 flygon5/mankey
chmod 711 flygon5/escavalier
chmod 513 flygon5/gliscor
chmod 732 flygon5/porygon
chmod 004 flygon5/larvitar
chmod 373 hariyama0
chmod 511 hariyama0/nidorina
chmod 764 hariyama0/whismur
chmod 624 hariyama0/silicoon
chmod 312 hariyama0/houndour
chmod 511 hariyama0/pichu
chmod 404 hariyama0/spinarak
chmod 333 oshawott5
chmod 377 oshawott5/zorua
chmod 311 oshawott5/elekid
chmod 711 oshawott5/ursaring
chmod 755 oshawott5/sunkern
chmod 440 oshawott5/accelegor
chmod 335 oshawott5/charmeleon
chmod 404 purugly3
chmod 444 rattata4
chmod 060 venonat3

##3

cp -R oshawott5 hariyama0/houndour

ln -s hariyama0 Copy_81

touch hariyama0/spinarakpurugly
cat purugly3 > hariyama0/spinarakpurugly

ln -s purugly3 hariyama0/spinarakpurugly

ln rattata4 hariyama0/spinarakrattata

cat hariyama0/silicoon hariyama0/spinarak > purugly3_83

cp purugly3 oshawott5/ursaring

##4

chmod -R +r *

cat rattata4 | wc -l  > /tmp/result 2> /tmp/errororo

#cat $(grep -rl "" --include="h*") | head -n 4

ls -tuR 2>&1 | grep '^h' | head -n 4

cat -n /hariyama0/* 2>/dev/null | grep -i 'e$'

ls -ltR 2>/dev/null | grep 'ra' | head -n 3

ls -lR 2>/dev/null | grep 'ru' | sort -k2,2n

ls -ltR 2>&1 | grep -i '5$' | tail -n 4

##5
chmod -R 500 *

rm -f ratatta4

rm -f oshawott5/accelgor

chmod -R 700 hariyama0
rm -rf hariyama0

chmod 700 oshawott5 oshawott5/sunkern
rmdir oshawott5/sunkern