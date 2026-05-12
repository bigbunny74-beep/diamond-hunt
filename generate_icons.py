#!/usr/bin/env python3
"""Run this locally to generate PNG icons from icon.svg.
Requires: pip install cairosvg"""
import os, sys
try:
    import cairosvg
    sizes = [(48,"mdpi"),(72,"hdpi"),(96,"xhdpi"),(144,"xxhdpi"),(192,"xxxhdpi")]
    for size, dpi in sizes:
        out = f"app/src/main/res/mipmap-{dpi}/ic_launcher.png"
        os.makedirs(os.path.dirname(out), exist_ok=True)
        cairosvg.svg2png(url="icon.svg", write_to=out, output_width=size, output_height=size)
        print(f"✓ {out} ({size}x{size})")
except ImportError:
    print("Install cairosvg: pip install cairosvg")
    print("Or replace with your own PNG icons manually.")
