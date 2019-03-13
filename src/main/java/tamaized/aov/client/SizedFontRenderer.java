package tamaized.aov.client;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.chars.CharArrayList;
import it.unimi.dsi.fastutil.chars.CharList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.DefaultGlyph;
import net.minecraft.client.gui.fonts.EmptyGlyph;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.gui.fonts.FontTexture;
import net.minecraft.client.gui.fonts.IGlyph;
import net.minecraft.client.gui.fonts.IGlyphInfo;
import net.minecraft.client.gui.fonts.TexturedGlyph;
import net.minecraft.client.gui.fonts.providers.IGlyphProvider;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.proxy.ClientProxy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SizedFontRenderer extends FontRenderer {

	private static final ResourceLocation id = new ResourceLocation("textures/font/ascii.png");
	private float size = 1F;

	public SizedFontRenderer() {
		super(Minecraft.getInstance().textureManager, new Font(Minecraft.getInstance().textureManager, id) {

			private final List<IGlyphProvider> glyphProviders = Lists.newArrayList();
			private final List<FontTexture> textures = Lists.newArrayList();
			private final Char2ObjectMap<TexturedGlyph> field_212463_j = new Char2ObjectOpenHashMap<>();
			private final Char2ObjectMap<IGlyph> glyphs = new Char2ObjectOpenHashMap<>();
			private final Int2ObjectMap<CharList> glyphsByWidth = new Int2ObjectOpenHashMap<>();
			private final IGlyph field_212461_c = () -> 4.0F;
			private final EmptyGlyph field_212460_b = new EmptyGlyph();
			private final Random field_212462_d = new Random();
			private TexturedGlyph fallbackGlyph;

			@Override
			public void setGlyphProviders(List<IGlyphProvider> glyphProvidersIn) {
				for (IGlyphProvider iglyphprovider : this.glyphProviders) {
					iglyphprovider.close();
				}

				this.glyphProviders.clear();
				this.deleteTextures();
				this.textures.clear();
				this.field_212463_j.clear();
				this.glyphs.clear();
				this.glyphsByWidth.clear();
				this.fallbackGlyph = this.createTexturedGlyph(DefaultGlyph.INSTANCE);
				Set<IGlyphProvider> set = Sets.newHashSet();

				for (char c0 = 0; c0 < '\uffff'; ++c0) {
					for (IGlyphProvider iglyphprovider1 : glyphProvidersIn) {
						IGlyph iglyph = (IGlyph) (c0 == ' ' ? field_212461_c : iglyphprovider1.func_212248_a(c0));
						if (iglyph != null) {
							set.add(iglyphprovider1);
							if (iglyph != DefaultGlyph.INSTANCE) {
								this.glyphsByWidth.computeIfAbsent(MathHelper.ceil(iglyph.getAdvance(false)), (p_212456_0_) -> new CharArrayList()).add(c0);
							}
							break;
						}
					}
				}

				glyphProvidersIn.stream().filter(set::contains).forEach(this.glyphProviders::add);
			}

			@Override
			public void deleteTextures() {
				for (FontTexture fonttexture : this.textures) {
					fonttexture.close();
				}
			}

			@Nonnull
			@Override
			public IGlyph findGlyph(char charIn) {
				return this.glyphs.computeIfAbsent(charIn, (p_212457_1_) -> (p_212457_1_ == 32 ? field_212461_c : this.func_212455_c((char) p_212457_1_)));
			}

			private IGlyphInfo func_212455_c(char p_212455_1_) {
				for (IGlyphProvider iglyphprovider : this.glyphProviders) {
					IGlyphInfo iglyphinfo = iglyphprovider.func_212248_a(p_212455_1_);
					if (iglyphinfo != null) {
						return iglyphinfo;
					}
				}

				return DefaultGlyph.INSTANCE;
			}

			@Nonnull
			@Override
			public TexturedGlyph getGlyph(char character) {
				return this.field_212463_j.computeIfAbsent(character, (p_212458_1_) -> (p_212458_1_ == 32 ? field_212460_b : this.createTexturedGlyph(this.func_212455_c((char) p_212458_1_))));
			}

			private TexturedGlyph createTexturedGlyph(IGlyphInfo glyphInfoIn) {
				for (FontTexture fonttexture : this.textures) {
					TexturedGlyph texturedglyph = fonttexture.createTexturedGlyph(glyphInfoIn);
					if (texturedglyph != null) {
						return texturedglyph;
					}
				}

				ResourceLocation textureLocation = new ResourceLocation(id.getNamespace(), id.getPath() + "/" + this.textures.size());
				FontTexture fonttexture1 = new FontTexture(textureLocation, glyphInfoIn.isColored()) {

					private final Entry entry = new Entry(0, 0, 256, 256);

					@Override
					@Nullable
					public TexturedGlyph createTexturedGlyph(IGlyphInfo glyphInfoIn_p) {
						if (glyphInfoIn_p.isColored() != glyphInfoIn.isColored()) {
							return null;
						} else {
							Entry fonttexture$entry = this.entry.func_211224_a(glyphInfoIn_p);
							if (fonttexture$entry != null) {
								this.bindTexture();
								glyphInfoIn_p.uploadGlyph(fonttexture$entry.xOffset, fonttexture$entry.yOffset);
								final float u0 = ((float) fonttexture$entry.xOffset + 0.01F) / 256.0F;
								final float u1 = ((float) fonttexture$entry.xOffset - 0.01F + (float) glyphInfoIn_p.getWidth()) / 256.0F;
								final float v0 = ((float) fonttexture$entry.yOffset + 0.01F) / 256.0F;
								final float v1 = ((float) fonttexture$entry.yOffset - 0.01F + (float) glyphInfoIn_p.getHeight()) / 256.0F;
								final float field_211240_f = glyphInfoIn_p.func_211198_f();
								final float field_211241_g = glyphInfoIn_p.func_211199_g();
								final float field_211242_h = glyphInfoIn_p.func_211200_h();
								final float field_211243_i = glyphInfoIn_p.func_211204_i();
								return new TexturedGlyph(textureLocation, u0, u1, v0, v1, field_211240_f, field_211241_g, field_211242_h, field_211243_i) {
									@Override
									public void render(TextureManager textureManagerIn, boolean isItalic, float x, float y, BufferBuilder buffer, float red, float green, float blue, float alpha) {
										float size = ClientProxy.getFontRenderer() == null ? 0 : ClientProxy.getFontRenderer().size;
										int i = 3;
										float f = x + field_211240_f;
										float f1 = x + field_211241_g;
										float f2 = field_211242_h - i;
										float f3 = field_211243_i - i;
										float f4 = y + f2;
										float f5 = y + f3 * size;
										float f6 = isItalic ? 1.0F - 0.25F * f2 : 0.0F;
										float f7 = isItalic ? 1.0F - 0.25F * f3 : 0.0F;
										buffer.pos((double) (f + f6), (double) f4, 0.0D).tex((double) u0, (double) v0).color(red, green, blue, alpha).endVertex();
										buffer.pos((double) (f + f7), (double) f5, 0.0D).tex((double) u0, (double) v1).color(red, green, blue, alpha).endVertex();
										buffer.pos((double) (f1 * size + f7), (double) f5, 0.0D).tex((double) u1, (double) v1).color(red, green, blue, alpha).endVertex();
										buffer.pos((double) (f1 * size + f6), (double) f4, 0.0D).tex((double) u1, (double) v0).color(red, green, blue, alpha).endVertex();
									}
								};
							} else {
								return null;
							}
						}
					}
				};
				this.textures.add(fonttexture1);
				Minecraft.getInstance().textureManager.loadTexture(fonttexture1.getTextureLocation(), fonttexture1);
				TexturedGlyph texturedglyph1 = fonttexture1.createTexturedGlyph(glyphInfoIn);
				return texturedglyph1 == null ? this.fallbackGlyph : texturedglyph1;
			}

			@Nonnull
			@Override
			public TexturedGlyph obfuscate(IGlyph glyph) {
				CharList charlist = this.glyphsByWidth.get(MathHelper.ceil(glyph.getAdvance(false)));
				return charlist != null && !charlist.isEmpty() ? this.getGlyph(charlist.get(field_212462_d.nextInt(charlist.size()))) : this.fallbackGlyph;
			}
		});
	}

	public SizedFontRenderer setSize(float s) {
		size = s;
		return this;
	}

	public float getSize() {
		return size;
	}

	public SizedFontRenderer reset() {
		return setSize(1F);
	}

	public float getFontHeight() {
		return FONT_HEIGHT * size;
	}

	@OnlyIn(Dist.CLIENT)
	static class Entry {
		final int xOffset;
		final int yOffset;
		final int field_211227_c;
		final int field_211228_d;
		Entry field_211229_e;
		Entry field_211230_f;
		boolean field_211231_g;

		private Entry(int p_i49711_1_, int p_i49711_2_, int p_i49711_3_, int p_i49711_4_) {
			this.xOffset = p_i49711_1_;
			this.yOffset = p_i49711_2_;
			this.field_211227_c = p_i49711_3_;
			this.field_211228_d = p_i49711_4_;
		}

		@Nullable
		Entry func_211224_a(IGlyphInfo p_211224_1_) {
			if (this.field_211229_e != null && this.field_211230_f != null) {
				Entry fonttexture$entry = this.field_211229_e.func_211224_a(p_211224_1_);
				if (fonttexture$entry == null) {
					fonttexture$entry = this.field_211230_f.func_211224_a(p_211224_1_);
				}

				return fonttexture$entry;
			} else if (this.field_211231_g) {
				return null;
			} else {
				int i = p_211224_1_.getWidth();
				int j = p_211224_1_.getHeight();
				if (i <= this.field_211227_c && j <= this.field_211228_d) {
					if (i == this.field_211227_c && j == this.field_211228_d) {
						this.field_211231_g = true;
						return this;
					} else {
						int k = this.field_211227_c - i;
						int l = this.field_211228_d - j;
						if (k > l) {
							this.field_211229_e = new Entry(this.xOffset, this.yOffset, i, this.field_211228_d);
							this.field_211230_f = new Entry(this.xOffset + i + 1, this.yOffset, this.field_211227_c - i - 1, this.field_211228_d);
						} else {
							this.field_211229_e = new Entry(this.xOffset, this.yOffset, this.field_211227_c, j);
							this.field_211230_f = new Entry(this.xOffset, this.yOffset + j + 1, this.field_211227_c, this.field_211228_d - j - 1);
						}

						return this.field_211229_e.func_211224_a(p_211224_1_);
					}
				} else {
					return null;
				}
			}
		}
	}
}
