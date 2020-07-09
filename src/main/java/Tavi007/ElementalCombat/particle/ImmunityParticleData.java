package Tavi007.ElementalCombat.particle;

import java.awt.Color;
import java.util.Locale;

import javax.annotation.Nonnull;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import Tavi007.ElementalCombat.StartupCommon;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.math.MathHelper;

/**
 * Modified class from MinecraftByExample (a mod from TheGreyGhost).
 *
 * The particle has two pieces of information which are used to customise it:
 *
 * 1) The colour (tint) which is used to change the hue of the particle
 * 2) The diameter of the particle
 *
 * This class is used to
 * 1) store this information, and
 * 2) transmit it between server and client (write and read methods), and
 * 3) parse it from a command string i.e. the /particle params
 */

public class ImmunityParticleData implements IParticleData {

	private Color tint;
	private double diameter;


	public ImmunityParticleData(Color tint, double diameter) {
		this.tint = tint;
		this.diameter = constrainDiameterToValidRange(diameter);
	}

	public Color getTint() {
		return tint;
	}

	/**
	 * @return get diameter of particle in meters
	 */
	public double getDiameter() {
		return diameter;
	}

	@Nonnull
	@Override
	public ParticleType<ImmunityParticleData> getType() {
		return StartupCommon.immunityParticleType;
	}

	// write the particle information to a PacketBuffer, ready for transmission to a client
	@Override
	public void write(PacketBuffer buf) {
		buf.writeInt(tint.getRed());
		buf.writeInt(tint.getGreen());
		buf.writeInt(tint.getBlue());
		buf.writeDouble(diameter);
	}

	// used for debugging I think; prints the data in human-readable format
	@Nonnull
	@Override
	public String getParameters() {
		return String.format(Locale.ROOT, "%s %.2f %i %i %i",
				this.getType().getRegistryName(), diameter, tint.getRed(), tint.getGreen(), tint.getBlue());
	}

	private static double constrainDiameterToValidRange(double diameter) {
		final double MIN_DIAMETER = 0.05;
		final double MAX_DIAMETER = 1.0;
		return MathHelper.clamp(diameter, MIN_DIAMETER, MAX_DIAMETER);
	}

	// The DESERIALIZER is used to construct CombatParticleData from either command line parameters or from a network packet

	public static final IDeserializer<ImmunityParticleData> DESERIALIZER = new IDeserializer<ImmunityParticleData>() {

		// parse the parameters for this particle from a /particle command
		@Nonnull
		@Override
		public ImmunityParticleData deserialize(@Nonnull ParticleType<ImmunityParticleData> type, @Nonnull StringReader reader) throws CommandSyntaxException {
			reader.expect(' ');
			double diameter = constrainDiameterToValidRange(reader.readDouble());

			final int MIN_COLOUR = 0;
			final int MAX_COLOUR = 255;
			reader.expect(' ');
			int red = MathHelper.clamp(reader.readInt(), MIN_COLOUR, MAX_COLOUR);
			reader.expect(' ');
			int green = MathHelper.clamp(reader.readInt(), MIN_COLOUR, MAX_COLOUR);
			reader.expect(' ');
			int blue = MathHelper.clamp(reader.readInt(), MIN_COLOUR, MAX_COLOUR);
			Color color = new Color(red, green, blue);

			return new ImmunityParticleData(color, diameter);
		}

		// read the particle information from a PacketBuffer after the client has received it from the server
		@Override
		public ImmunityParticleData read(@Nonnull ParticleType<ImmunityParticleData> type, PacketBuffer buf) {
			// warning! never trust the data read in from a packet buffer.

			final int MIN_COLOUR = 0;
			final int MAX_COLOUR = 255;
			int red = MathHelper.clamp(buf.readInt(), MIN_COLOUR, MAX_COLOUR);
			int green = MathHelper.clamp(buf.readInt(), MIN_COLOUR, MAX_COLOUR);
			int blue = MathHelper.clamp(buf.readInt(), MIN_COLOUR, MAX_COLOUR);
			Color color = new Color(red, green, blue);

			double diameter = constrainDiameterToValidRange(buf.readDouble());

			return new ImmunityParticleData(color, diameter);
		}
	};
}
